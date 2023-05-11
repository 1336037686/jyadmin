package com.jyadmin.generate.service.impl;
import java.util.*;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.jyadmin.consts.ResultStatus;
import com.jyadmin.exception.ApiException;
import com.jyadmin.generate.common.constant.CodeGenerateConstant;
import com.jyadmin.generate.domain.*;
import com.jyadmin.generate.model.vo.TableOptionRespVO;
import com.jyadmin.generate.model.vo.TableQueryReqVO;
import com.jyadmin.generate.service.*;
import com.jyadmin.util.StringUtil;
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
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public boolean updateTable(String tableName) {
        return false;
    }

    @Override
    public boolean removeByIds(Set<String> ids) {
        return false;
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
                .setPageViewPath(tableNameCamelCase);
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
            boolean isNullable = Objects.equals(rs.getString("IS_NULLABLE"), "YES"); // YES 可以为null，NO 不可以为null
            boolean isAutoIncrement = Objects.equals(rs.getString("IS_AUTOINCREMENT"), "NO"); // yea 可 no 不可自增
            boolean isPrimaryKey = isPk(table.getTableName(), columnName, metaData);
            String defaultValue = rs.getString("COLUMN_DEF");
            String remarks = rs.getString("REMARKS");

            CodeGenerateField field = new CodeGenerateField()
            .setTableId(table.getId()).setFieldName(columnName)
            .setFieldType(typeName).setFieldLength(String.valueOf(columnSize))
            .setNonNull(String.valueOf(isNullable ? 0 : 1)).setPk(String.valueOf(isPrimaryKey ? 1 : 0))
            .setAutoIncre(String.valueOf(isAutoIncrement ? 0 : 1)).setDefaultValue(defaultValue)
            .setFieldRemark(remarks).setSort(index++);

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
            // 设置属性默认配置数据
            CodeGenerateFieldConfig config = new CodeGenerateFieldConfig();
            config.setFieldId(field.getId());
            config.setJavaType(javaType);
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

}
