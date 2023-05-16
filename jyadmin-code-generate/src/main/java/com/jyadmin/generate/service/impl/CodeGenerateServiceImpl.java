package com.jyadmin.generate.service.impl;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.engine.velocity.VelocityEngine;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jyadmin.consts.ResultStatus;
import com.jyadmin.exception.ApiException;
import com.jyadmin.generate.common.constant.CodeGenerateConstant;
import com.jyadmin.generate.common.utils.VelocityUtils;
import com.jyadmin.generate.domain.*;
import com.jyadmin.generate.model.dto.TemplateContextDTO;
import com.jyadmin.generate.model.dto.TemplateModelDTO;
import com.jyadmin.generate.model.vo.TableOptionRespVO;
import com.jyadmin.generate.model.vo.TableQueryReqVO;
import com.jyadmin.generate.model.vo.UserConfigReqVO;
import com.jyadmin.generate.service.*;
import com.jyadmin.util.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.util.stream.Collectors;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-05-11 10:31 <br>
 * @description: CodeGeneratorServiceImpl <br>
 */
@Slf4j
@Service
public class CodeGenerateServiceImpl implements CodeGenerateService {

    @Resource
    private DataSource dataSource;

    @Resource
    private CodeGenerateTableService codeGenerateTableService;

    @Resource
    private CodeGenerateTableConfigService codeGenerateTableConfigService;

    @Resource
    private CodeGenerateFieldService codeGenerateFieldService;

    @Resource
    private CodeGenerateFieldConfigService codeGenerateFieldConfigService;

    @Resource
    private CodeGenerateFieldTypeService codeGenerateFieldTypeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveTable(String tableName) {
        List<CodeGenerateTable> checkExist = codeGenerateTableService.list(new LambdaQueryWrapper<CodeGenerateTable>().eq(CodeGenerateTable::getTableName, tableName));
        if (CollectionUtils.isNotEmpty(checkExist)) throw new ApiException(ResultStatus.CODE_GEN_TABLE_ALREADY_EXIST);
        try (Connection conn = dataSource.getConnection()) {
            // 获取基础表信息
            CodeGenerateTable table = getCodeGenerateTable(tableName, conn);
            Assert.notNull(table, "当前数据库表不存在");
            codeGenerateTableService.save(table);
            // 获取默认基础表默认配置
            CodeGenerateTableConfig tableConfig = getCodeGenerateTableConfig(table);
            codeGenerateTableConfigService.save(tableConfig);
            // 获取表字段信息
            List<CodeGenerateField> fields = getCodeGenerateFields(table, conn);
            codeGenerateFieldService.saveBatch(fields);
            // 获取表字段默认配置
            List<CodeGenerateFieldConfig> fieldConfigs = getCodeGenerateFieldConfigs(table, fields);
            codeGenerateFieldConfigService.saveBatch(fieldConfigs);
        } catch (Exception e) {
            log.error(ThrowableUtil.getStackTrace(e));
            throw new ApiException(ResultStatus.CODE_GEN_TABLE_LOAD_ERROR);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateTable(String tableId) {
        // 获取旧数据 数据库表所有数据，表信息、表配置、字段信息、字段配置
        CodeGenerateTable oldTable = codeGenerateTableService.getById(tableId);
        CodeGenerateTableConfig oldTableConfig = codeGenerateTableConfigService.getOne(new LambdaQueryWrapper<CodeGenerateTableConfig>().eq(CodeGenerateTableConfig::getTableId, oldTable.getId()));
        List<CodeGenerateField> oldFields = codeGenerateFieldService.list(new LambdaQueryWrapper<CodeGenerateField>().eq(CodeGenerateField::getTableId, oldTable.getId()));
        List<String> oldFieldIds = oldFields.stream().map(CodeGenerateField::getId).collect(Collectors.toList());
        List<CodeGenerateFieldConfig> oldFieldConfigs = codeGenerateFieldConfigService.list(new LambdaQueryWrapper<CodeGenerateFieldConfig>().in(CodeGenerateFieldConfig::getFieldId, oldFieldIds));
        // 获取实时数据 当前数据库表所有数据，表信息、表配置、字段信息、字段配置
        CodeGenerateTable newTable = null;
        List<CodeGenerateField> newFields = null;
        try (Connection conn = dataSource.getConnection()) {
            newTable = getCodeGenerateTable(oldTable.getTableName(), conn);
            Assert.notNull(newTable, "当前数据库表不存在");
            newFields = getCodeGenerateFields(oldTable, conn);
        } catch (Exception e) {
            log.error(ThrowableUtil.getStackTrace(e));
            throw new ApiException(ResultStatus.CODE_GEN_TABLE_LOAD_ERROR);
        }
        // 1、更新表数据, 复制旧的属性过来，有值的以新的为主，null的则以旧为主
        BeanUtil.copyProperties(newTable, oldTable, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        codeGenerateTableService.updateById(oldTable);
        // 2、更新表属性
        // 2.1 去除已经不存在的字段数据
        List<String> newFieldNames = newFields.stream().map(CodeGenerateField::getFieldName).collect(Collectors.toList());
        // 2.1.1 获取不存在的字段信息
        List<CodeGenerateField> fieldsToDelete = oldFields.stream().filter(x -> !newFieldNames.contains(x.getFieldName())).collect(Collectors.toList());
        // 2.1.2 获取不存在的字段配置
        List<String> fieldIdsToDelete = fieldsToDelete.stream().map(CodeGenerateField::getId).collect(Collectors.toList());
        List<CodeGenerateFieldConfig> fieldConfigsToDelete = oldFieldConfigs.stream().filter(x -> fieldIdsToDelete.contains(x.getFieldId())).collect(Collectors.toList());
        // 2.1.3 删除多余数据
        codeGenerateFieldService.removeByIds(fieldsToDelete);
        codeGenerateFieldConfigService.removeByIds(fieldConfigsToDelete);
        // 2.2 添加新增的字段
        List<String> oldFieldNames = oldFields.stream().map(CodeGenerateField::getFieldName).collect(Collectors.toList());
        // 2.2.1 获取并创建不存在的字段信息
        List<CodeGenerateField> fieldsToSave = newFields.stream().filter(x -> !oldFieldNames.contains(x.getFieldName())).collect(Collectors.toList());
        codeGenerateFieldService.saveBatch(fieldsToSave);
        // 2.2.2 创建不存在的字段配置
        List<CodeGenerateFieldConfig> fieldConfigsToSave = getCodeGenerateFieldConfigs(oldTable, fieldsToSave);
        codeGenerateFieldConfigService.saveBatch(fieldConfigsToSave);
        // 2.3 更新原始存在字段
        List<CodeGenerateField> fieldsToUpdate = oldFields.stream().filter(x -> newFieldNames.contains(x.getFieldName())).collect(Collectors.toList());
        Map<String, List<CodeGenerateField>> newFieldMaps = newFields.stream().collect(Collectors.groupingBy(CodeGenerateField::getFieldName));
        // 2.3.1 更新原始存在字段信息
        fieldsToUpdate = fieldsToUpdate.stream().peek(x -> {
            CodeGenerateField newField = newFieldMaps.get(x.getFieldName()).get(0);
            // 拷贝最新的属性
            BeanUtil.copyProperties(newField, x, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        }).collect(Collectors.toList());
        codeGenerateFieldService.updateBatchById(fieldsToUpdate);
        // 2.3.2 更新原始存在字段配置
        Map<String, List<CodeGenerateFieldConfig>> oldFieldConfigMaps = oldFieldConfigs.stream().collect(Collectors.groupingBy(CodeGenerateFieldConfig::getFieldId));
        List<CodeGenerateFieldConfig> fieldConfigsToUpdate = fieldsToUpdate.stream().map(x -> updateCodeGenerateFieldConfigs(x, oldFieldConfigMaps.get(x.getId()).get(0))).collect(Collectors.toList());
        codeGenerateFieldConfigService.updateBatchById(fieldConfigsToUpdate);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(Set<String> ids) {
        // 查找所有表数据
        List<CodeGenerateTable> tables = codeGenerateTableService.listByIds(ids);
        // 查找所有表配置数据
        List<CodeGenerateTableConfig> tableConfigs = codeGenerateTableConfigService.list(new LambdaQueryWrapper<CodeGenerateTableConfig>()
                .in(CodeGenerateTableConfig::getTableId, tables.stream().map(CodeGenerateTable::getId).collect(Collectors.toList()))
        );
        // 查找所有属性数据
        List<CodeGenerateField> fields = codeGenerateFieldService.list(new LambdaQueryWrapper<CodeGenerateField>()
                .in(CodeGenerateField::getTableId, tables.stream().map(CodeGenerateTable::getId).collect(Collectors.toList()))
        );
        // 查找所有属性配置数据
        List<CodeGenerateFieldConfig> fieldConfigs = codeGenerateFieldConfigService.list(new LambdaQueryWrapper<CodeGenerateFieldConfig>()
                .in(CodeGenerateFieldConfig::getFieldId, fields.stream().map(CodeGenerateField::getId).collect(Collectors.toList()))
        );
        codeGenerateTableService.removeByIds(tables);
        codeGenerateTableConfigService.removeByIds(tableConfigs);
        codeGenerateFieldService.removeByIds(fields);
        codeGenerateFieldConfigService.removeByIds(fieldConfigs);
        return true;
    }

    @Override
    public boolean getTableExist(String tableId) {
        CodeGenerateTable table = this.codeGenerateTableService.getById(tableId);
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet rs = metaData.getTables(null, null, table.getTableName(), null);
            boolean hasNext = rs.next();
            rs.close();
            return hasNext;
        } catch (Exception e) {
            log.error(ThrowableUtil.getStackTrace(e));
            throw new ApiException(ResultStatus.CODE_GEN_TABLE_LOAD_ERROR);
        }
    }

    @Override
    public boolean updateTableConfig(UserConfigReqVO vo) {
        if (Objects.nonNull(vo.getTableConfig())) codeGenerateTableConfigService.updateById(vo.getTableConfig());
        if (CollectionUtils.isNotEmpty(vo.getFieldConfigs())) codeGenerateFieldConfigService.updateBatchById(vo.getFieldConfigs());
        return true;
    }

    @Override
    public boolean generateCode(String tableId) {
        try {
            TemplateContextDTO templateContext = buildTemplateContext(tableId);
            VelocityEngine velocityEngine = VelocityUtils.createVelocityEngine();
            // 准备数据模型
            Map<String, Object> model = VelocityUtils.obj2MapModel(templateContext.getModel());
            String packagePath = templateContext.getModel().getPackageName().replace(".", "/");
            Map<String, Template> templateMaps = Maps.newHashMap();
            Map<String, String> templatePathMaps = Maps.newHashMap();
            // controller
            String templateKeyController = templateContext.getModel().getRealTableNameUpperCamelCase() + "Controller.java";
            templateMaps.put(templateKeyController, velocityEngine.getTemplate("template/simple-controller.java.vm"));
            templatePathMaps.put(templateKeyController, "src/" + packagePath + "/controller");
            // domain
            String templateKeyDomain = templateContext.getModel().getRealTableNameUpperCamelCase() + ".java";
            templateMaps.put(templateKeyDomain, velocityEngine.getTemplate("template/simple-domain.java.vm"));
            templatePathMaps.put(templateKeyDomain, "src/" + packagePath + "/domain");
            // vo.createReqVO
            String templateKeyModelCreateReqVO = templateContext.getModel().getRealTableNameUpperCamelCase() + "CreateReqVO.java";
            templateMaps.put(templateKeyModelCreateReqVO, velocityEngine.getTemplate("template/simple-createReqVO.java.vm"));
            templatePathMaps.put(templateKeyModelCreateReqVO, "src/" + packagePath + "/model/vo");
            // vo.updateReqVO
            String templateKeyModelUpdateReqVO = templateContext.getModel().getRealTableNameUpperCamelCase() + "UpdateReqVO.java";
            templateMaps.put(templateKeyModelUpdateReqVO, velocityEngine.getTemplate("template/simple-updateReqVO.java.vm"));
            templatePathMaps.put(templateKeyModelUpdateReqVO, "src/" + packagePath + "/model/vo");
            // vo.queryReqVO
            String templateKeyModelQueryReqVO = templateContext.getModel().getRealTableNameUpperCamelCase() + "QueryReqVO.java";
            templateMaps.put(templateKeyModelQueryReqVO, velocityEngine.getTemplate("template/simple-queryReqVO.java.vm"));
            templatePathMaps.put(templateKeyModelQueryReqVO, "src/" + packagePath + "/model/vo");
            // service
            String templateKeyService = templateContext.getModel().getRealTableNameUpperCamelCase() + "Service.java";
            templateMaps.put(templateKeyService, velocityEngine.getTemplate("template/simple-service.java.vm"));
            templatePathMaps.put(templateKeyService, "src/" + packagePath + "/service");
            // serviceImpl
            String templateKeyServiceImpl = templateContext.getModel().getRealTableNameUpperCamelCase() + "ServiceImpl.java";
            templateMaps.put(templateKeyServiceImpl, velocityEngine.getTemplate("template/simple-serviceImpl.java.vm"));
            templatePathMaps.put(templateKeyServiceImpl, "src/" + packagePath + "/service/impl");
            // mapper
            String templateKeyMapper = templateContext.getModel().getRealTableNameUpperCamelCase() + "Mapper.java";
            templateMaps.put(templateKeyMapper, velocityEngine.getTemplate("template/simple-mapper.java.vm"));
            templatePathMaps.put(templateKeyMapper, "src/" + packagePath + "/mapper");
            // mapper.xml
            String templateKeyMapperXML = templateContext.getModel().getRealTableNameUpperCamelCase() + "Mapper.xml";
            templateMaps.put(templateKeyMapperXML, velocityEngine.getTemplate("template/simple-mapper.xml.vm"));
            templatePathMaps.put(templateKeyMapperXML, "resources/mapper");

            for (String key : templateMaps.keySet()) {
                Template template = templateMaps.get(key);
                String basePath = templatePathMaps.get(key);
                // 渲染模板
                String absPath = "D:\\code\\" + basePath + "\\" + key;
                Path pathToFile = Paths.get(absPath);
                Files.createDirectories(pathToFile.getParent());
                Files.createFile(pathToFile);
                Writer writer = new FileWriter(absPath);
                template.render(model, writer);
                // 输出结果
                writer.flush();
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 构建模板生成上下文信息
     * @param tableId
     * @return
     */
    public TemplateContextDTO buildTemplateContext(String tableId) {
        // 获取数据 数据库表所有数据，表信息、表配置、字段信息、字段配置
        CodeGenerateTable table = codeGenerateTableService.getById(tableId);
        CodeGenerateTableConfig tableConfig = codeGenerateTableConfigService.getOne(new LambdaQueryWrapper<CodeGenerateTableConfig>().eq(CodeGenerateTableConfig::getTableId, table.getId()));
        List<CodeGenerateField> fields = codeGenerateFieldService.list(new LambdaQueryWrapper<CodeGenerateField>().eq(CodeGenerateField::getTableId, table.getId()));
        List<String> oldFieldIds = fields.stream().map(CodeGenerateField::getId).collect(Collectors.toList());
        List<CodeGenerateFieldConfig> fieldConfigs = codeGenerateFieldConfigService.list(new LambdaQueryWrapper<CodeGenerateFieldConfig>().in(CodeGenerateFieldConfig::getFieldId, oldFieldIds));
        Map<String, List<CodeGenerateFieldConfig>> fieldConfigMaps = fieldConfigs.stream().collect(Collectors.groupingBy(CodeGenerateFieldConfig::getFieldId));
        List<String> importPackages = fieldConfigs.stream().map(CodeGenerateFieldConfig::getClassName).filter(StringUtils::isNotBlank).collect(Collectors.toList());

        // fields
        List<TemplateModelDTO.FieldTemplateModel> fieldTemplateModels = Lists.newArrayList();
        for (CodeGenerateField field : fields) {
            CodeGenerateFieldConfig fieldConfig = fieldConfigMaps.get(field.getId()).get(0);
            TemplateModelDTO.FieldTemplateModel fieldTemplateModel = new TemplateModelDTO.FieldTemplateModel();
            BeanUtil.copyProperties(field, fieldTemplateModel);
            BeanUtil.copyProperties(fieldConfig, fieldTemplateModel);
            fieldTemplateModel.setRealFieldNameLowCamelCase(StrUtil.toCamelCase(field.getFieldName()));
            fieldTemplateModel.setRealFieldNameUpperCamelCase(StrUtil.upperFirst(StrUtil.toCamelCase(field.getFieldName())));
            fieldTemplateModels.add(fieldTemplateModel);
        }

        // table
        TemplateModelDTO templateModelDTO = new TemplateModelDTO();
        BeanUtil.copyProperties(table, templateModelDTO);
        BeanUtil.copyProperties(tableConfig, templateModelDTO);
        templateModelDTO.setRealTableNameLowCamelCase(StrUtil.toCamelCase(table.getTableName()));
        templateModelDTO.setRealTableNameUpperCamelCase(StrUtil.upperFirst(StrUtil.toCamelCase(table.getTableName())));
        templateModelDTO.setImportPackages(importPackages);
        templateModelDTO.setClassName(StrUtil.upperFirst(StrUtil.toCamelCase(table.getTableName())));
        templateModelDTO.setCurrTime(DateUtil.format(LocalDateTime.now(), DatePattern.NORM_DATETIME_PATTERN));
        templateModelDTO.setDescription(table.getTableRemark());
        templateModelDTO.setVersion(CodeGenerateConstant.TABLE_CONFIG_VERSION);
        templateModelDTO.setFields(fieldTemplateModels);

        return new TemplateContextDTO().setModel(templateModelDTO);
    }

    @Override
    public List<TableOptionRespVO> getTableOptionsList(TableQueryReqVO vo) {
        List<TableOptionRespVO> res = Lists.newArrayList();
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet rs = metaData.getTables(conn.getCatalog(), null, null, new String[] { "TABLE" });
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                String remarks = rs.getString("REMARKS");
                res.add(new TableOptionRespVO().setTableName(tableName).setTableRemark(remarks));
            }
            rs.close();
        } catch (SQLException e) {
            log.error(ThrowableUtil.getStackTrace(e));
            throw new ApiException(ResultStatus.CODE_GEN_QUERY_TABLE_METADATA_FAIL);
        }
        // 去除已存在记录的表
        List<CodeGenerateTable> list = codeGenerateTableService.list();
        List<String> existTableList = list.stream().map(CodeGenerateTable::getTableName).collect(Collectors.toList());
        return res.stream().filter(x -> !existTableList.contains(x.getTableName())).collect(Collectors.toList());
    }

    /**
     * 获取表信息
     * @param tableName 表名
     * @return /
     */
    public CodeGenerateTable getCodeGenerateTable(String tableName, Connection conn) throws SQLException {

        String tableRemark = "", engine = "", charset = "", collation = "", createTableSql = "";
        Boolean tableExist = false;
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet rs = metaData.getTables(conn.getCatalog(), null, tableName, new String[] { "TABLE" });

        while (rs.next()) {
            tableExist = true;
            tableRemark = rs.getString("REMARKS");
            Statement stmt = conn.createStatement();
            ResultSet tableRs = stmt.executeQuery("SHOW CREATE TABLE " + tableName);
            if (tableRs.next()) {
                createTableSql = tableRs.getString(2);
                String[] lines = createTableSql.split("\n");
                String lastLine = lines[lines.length - 1] + " ";
                if (lastLine.contains("ENGINE=")) engine = lastLine.substring(lastLine.indexOf("ENGINE=") + 7, lastLine.indexOf(" ", lastLine.indexOf("ENGINE=") + 7));
                if (lastLine.contains("CHARSET=")) charset = lastLine.substring(lastLine.indexOf("CHARSET=") + 8, lastLine.indexOf(" ", lastLine.indexOf("CHARSET=") + 8));
                if (lastLine.contains("COLLATE=")) collation = lastLine.substring(lastLine.indexOf("COLLATE=") + 8, lastLine.indexOf(" ", lastLine.indexOf("COLLATE=") + 8));
            }
            tableRs.close();
            stmt.close();
        }
        rs.close();
        if (Boolean.FALSE.equals(tableExist)) return null;
        return new CodeGenerateTable()
                .setTableName(tableName).setTableRemark(tableRemark)
                .setTableEngine(engine).setTableCharset(charset).setTableOrder(collation)
                .setTableDdl(createTableSql);
    }

    /**
     * 获取表默认配置信息
     * @param table CodeGenerateTable
     * @return /
     */
    public CodeGenerateTableConfig getCodeGenerateTableConfig(CodeGenerateTable table) {
        String tableNameCamelCase = StrUtil.toCamelCase(table.getTableName());

        String tableId = table.getId();
        String tableRemark = table.getTableRemark();
        if (StringUtils.isBlank(tableRemark)) tableRemark = tableNameCamelCase;
        String packageName = CodeGenerateConstant.TABLE_CONFIG_PACKAGE_NAME_PREFIX + StrUtil.DOT + tableNameCamelCase;
        String publicUrl = CodeGenerateConstant.TABLE_CONFIG_PUBLIC_URL + StrUtil.SLASH + tableNameCamelCase;

        return new CodeGenerateTableConfig()
                .setTableId(tableId).setAuthor(CodeGenerateConstant.TABLE_CONFIG_Author)
                .setPackageName(packageName).setPublicUrl(publicUrl).setSwaggerApiValue(tableRemark)
                .setSwaggerApiTag(CodeGenerateConstant.TABLE_CONFIG_SWAGGER_API_TAG_PREFIX + tableRemark)
                .setPageViewPath(tableNameCamelCase).setTableType(CodeGenerateConstant.TABLE_CONFIG_TYPE);
    }

    /**
     * 获取表字段信息
     * @param table 表信息
     * @return /
     */
    public List<CodeGenerateField> getCodeGenerateFields(CodeGenerateTable table, Connection conn) throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet rs = metaData.getColumns(null, null, table.getTableName(), null);
        List<CodeGenerateField> res = Lists.newArrayList();
        int index = 1;
        while (rs.next()) {
            String columnName = rs.getString("COLUMN_NAME");
            String typeName = rs.getString("TYPE_NAME");
            Integer columnSize = rs.getInt("COLUMN_SIZE");
            Integer decimalDigits = rs.getInt("DECIMAL_DIGITS");
            boolean isNullable = Objects.equals(rs.getString("IS_NULLABLE"), "YES"); // YES 可以为null，NO 不可以为null
            boolean isAutoIncrement = Objects.equals(rs.getString("IS_AUTOINCREMENT"), "NO"); // yea 可 no 不可自增
            boolean isPrimaryKey = isPk(table.getTableName(), columnName, metaData);
            String defaultValue = rs.getString("COLUMN_DEF");
            String remarks = rs.getString("REMARKS");

            CodeGenerateField field = new CodeGenerateField()
                    .setTableId(table.getId()).setFieldName(columnName)
                    .setFieldType(typeName).setFieldLength(String.valueOf(columnSize))
                    .setFieldDecimalDigits(String.valueOf(decimalDigits)).setNonNull(String.valueOf(isNullable ? 0 : 1))
                    .setPk(String.valueOf(isPrimaryKey ? 1 : 0)).setAutoIncre(String.valueOf(isAutoIncrement ? 0 : 1))
                    .setDefaultValue(defaultValue).setFieldRemark(remarks).setSort(index++);
            res.add(field);
        }
        rs.close();
        return res;
    }

    /**
     * 判断当前字段是不是主键
     * @param tableName 表名
     * @param columnName 字段名
     * @param metaData 元数据
     * @return /
     * @throws SQLException
     */
    public boolean isPk(String tableName, String columnName, DatabaseMetaData metaData) throws SQLException {
        boolean isPrimaryKey = false;
        ResultSet pkRs = metaData.getPrimaryKeys(null, null, tableName);
        while (pkRs.next()) {
            if (pkRs.getString("COLUMN_NAME").equals(columnName)) {
                isPrimaryKey = true;
                break;
            }
        }
        pkRs.close();
        return isPrimaryKey;
    }

    /**
     * 获取表字段默认配置信息
     * @param table 表信息
     * @param fields 字段信息
     * @return /
     */
    public List<CodeGenerateFieldConfig> getCodeGenerateFieldConfigs(CodeGenerateTable table, List<CodeGenerateField> fields) {
        List<CodeGenerateFieldConfig> res = Lists.newArrayList();
        for (CodeGenerateField field : fields) {
            boolean isIgnoreField = CodeGenerateConstant.IGNORE_FIELDS.contains(field.getFieldName());
            // 获取对应的Java类型
            CodeGenerateFieldType fieldType = codeGenerateFieldTypeService.getOne(
                    new LambdaQueryWrapper<CodeGenerateFieldType>().eq(CodeGenerateFieldType::getJdbcType, field.getFieldType())
            );
            String javaType = Objects.isNull(fieldType) ? null : fieldType.getJavaType();
            String className = Objects.isNull(fieldType) ? null : fieldType.getClassName();
            // 设置属性默认配置数据
            CodeGenerateFieldConfig config = new CodeGenerateFieldConfig();
            config.setFieldId(field.getId());
            config.setJavaType(javaType);
            config.setClassName(className);
            config.setFieldAlias(field.getFieldRemark());
            // 页面 | 详情展示
            config.setShowPage(isIgnoreField ? CodeGenerateConstant.EnableStatus.NO.getValue() : CodeGenerateConstant.FIELD_CONFIG_SHOW_PAGE);
            config.setShowDetail(isIgnoreField ? CodeGenerateConstant.EnableStatus.NO.getValue() : CodeGenerateConstant.FIELD_CONFIG_SHOW_DETAIL);
            // 表单展示
            config.setShowForm(isIgnoreField ? CodeGenerateConstant.EnableStatus.NO.getValue() : CodeGenerateConstant.FIELD_CONFIG_SHOW_FORM);
            config.setFormRequire(isIgnoreField ? CodeGenerateConstant.EnableStatus.NO.getValue() : CodeGenerateConstant.FIELD_CONFIG_REQUIRE);
            config.setFormType(isIgnoreField ? null : CodeGenerateConstant.FIELD_CONFIG_FORM_TYPE);
            // 查询展示
            config.setShowQuery(isIgnoreField ? CodeGenerateConstant.EnableStatus.NO.getValue() : CodeGenerateConstant.FIELD_CONFIG_SHOW_QUERY);
            config.setFormSelectMethod(isIgnoreField ? null : CodeGenerateConstant.FIELD_CONFIG_FORM_SELECT_METHOD);
            // 是否忽略字段
            config.setFieldIgnore(isIgnoreField ? CodeGenerateConstant.EnableStatus.YES.getValue() : CodeGenerateConstant.EnableStatus.NO.getValue());
            res.add(config);
        }
        return res;
    }

    /**
     * 更新旧表字段配置信息
     * @return /
     */
    public CodeGenerateFieldConfig updateCodeGenerateFieldConfigs(CodeGenerateField field, CodeGenerateFieldConfig config) {
        // 获取对应的Java类型
        CodeGenerateFieldType fieldType = codeGenerateFieldTypeService.getOne(
                new LambdaQueryWrapper<CodeGenerateFieldType>().eq(CodeGenerateFieldType::getJdbcType, field.getFieldType())
        );
        String javaType = Objects.isNull(fieldType) ? null : fieldType.getJavaType();
        String className = Objects.isNull(fieldType) ? null : fieldType.getClassName();
        // 设置更新属性类型
        config.setJavaType(javaType);
        config.setClassName(className);
        return config;
    }

}
