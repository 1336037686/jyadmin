package com.jyadmin.generate.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.generate.domain.CodeGenerateFieldType;
import com.jyadmin.generate.domain.CodeGenerateTable;
import com.jyadmin.generate.model.vo.*;
import com.jyadmin.generate.service.CodeGenerateFieldTypeService;
import com.jyadmin.generate.service.CodeGenerateService;
import com.jyadmin.generate.service.CodeGenerateTableService;
import com.jyadmin.util.PageUtil;
import com.jyadmin.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 代码生成器
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-05-10 11:18 <br>
 * @description: CodeGeneratorController <br>
 */
@Slf4j
@Api(value = "代码生成器", tags = {"系统：代码生成器接口"})
@RequestMapping("/api/code-generator")
@RestController
public class CodeGenerateController {

    @Resource
    private CodeGenerateService codeGenerateService;

    @Resource
    private CodeGenerateTableService codeGenerateTableService;

    @Resource
    private CodeGenerateFieldTypeService codeGenerateFieldTypeService;

    @ApiOperation(value = "导入数据库表", notes = "")
    @PostMapping("/create/table/{tableName}")
    public Result<Object> doCreate(@PathVariable("tableName") String tableName) {
        return ResultUtil.toResult(codeGenerateService.saveTable(tableName));
    }

    @ApiOperation(value = "同步数据库表", notes = "")
    @PutMapping("/update/table/{tableId}")
    public Result<Object> doUpdate(@PathVariable("tableId") String tableId) {
        return ResultUtil.toResult(codeGenerateService.updateTable(Long.parseLong(tableId)));
    }

    @ApiOperation(value = "删除数据库表", notes = "")
    @DeleteMapping("/remove/table")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)) return Result.fail();
        Set<Long> newIds = ids.stream().map(Long::parseLong).collect(Collectors.toSet());
        return ResultUtil.toResult(codeGenerateService.removeByIds(newIds));
    }

    @ApiOperation(value = "代码生成接口", notes = "")
    @GetMapping("/generate/{tableId}")
    public void doGenerateCode(@PathVariable("tableId") String tableId, HttpServletResponse response) {
        codeGenerateService.generateCode(Long.parseLong(tableId), response);
    }

    @ApiOperation(value = "代码预览接口", notes = "")
    @GetMapping("/generate-preview/{tableId}")
    public Result<List<CodePreviewResVO>> doGeneratePreviewCode(@PathVariable("tableId") String tableId) {
        List<CodePreviewResVO> codePreviewResVOS = codeGenerateService.generatePreviewCode(Long.parseLong(tableId));
        return Result.ok(codePreviewResVOS);
    }

    @ApiOperation(value = "更新数据库表配置", notes = "")
    @PutMapping("/update/table-config")
    public Result<Object> doUpdateTableConfig(@RequestBody @Valid UserConfigReqVO vo) {
        return ResultUtil.toResult(codeGenerateService.updateTableConfig(vo));
    }

    @ApiOperation(value = "分页获取数据库表列表", notes = "")
    @GetMapping("/query-table-exist/{tableId}")
    public Result<Object> doQueryTableExist(@PathVariable("tableId") String tableId) {
        return Result.ok(codeGenerateService.getTableExist(Long.parseLong(tableId)));
    }

    @ApiOperation(value = "分页获取数据库表列表（选择器）", notes = "")
    @GetMapping("/query-table-options")
    public PageResult<TableOptionRespVO> doQueryTableOptionsPage(TableQueryReqVO vo) {
        List<TableOptionRespVO> list = codeGenerateService.getTableOptionsList(vo);
        return PageUtil.toPageResult(list, vo);
    }

    @ApiOperation(value = "分页获取数据库表列表（展示）", notes = "")
    @GetMapping("/query")
    public PageResult<CodeGenerateTable> doQueryTablePage(TableQueryReqVO vo) {
        return PageUtil.toPageResult(
                codeGenerateTableService.page(
                        new Page<>(vo.getPageNumber(), vo.getPageSize()),
                        new LambdaQueryWrapper<CodeGenerateTable>()
                                .eq(StringUtils.isNotBlank(vo.getTableName()), CodeGenerateTable::getTableName, vo.getTableName())
                                .orderByDesc(CodeGenerateTable::getCreateTime)
                )
        );
    }

    @ApiOperation(value = "获取数据库表具体配置", notes = "")
    @GetMapping("/query/{tableId}")
    public Result<UserConfigResVO> doQueryTableConfig(@PathVariable("tableId") String tableId) {
        return Result.ok(codeGenerateService.getTableConfig(Long.parseLong(tableId)));
    }

    @ApiOperation(value = "根据ID获取单一数据库表信息", notes = "")
    @GetMapping("/query-sigle-table/{tableId}")
    public Result<CodeGenerateTable> doQuerySigleTableById(@PathVariable("tableId") String tableId) {
        return Result.ok(codeGenerateTableService.getById(tableId));
    }

    @ApiOperation(value = "获取属性对应的Java类型列表", notes = "")
    @GetMapping("/query-field-type")
    public Result<List<CodeGenerateJavaFieldTypeResVO>> doQueryJavaFieldType() {
        List<CodeGenerateFieldType> generateFieldTypes = codeGenerateFieldTypeService.list(
                new LambdaQueryWrapper<CodeGenerateFieldType>().orderByDesc(CodeGenerateFieldType::getCreateTime)
        );
        ArrayList<CodeGenerateJavaFieldTypeResVO> javaFieldTypeResVOS = new ArrayList<>(generateFieldTypes.stream()
                .map(x -> new CodeGenerateJavaFieldTypeResVO().setJavaType(x.getJavaType()).setClassName(x.getClassName()))
                .collect(Collectors.toMap(CodeGenerateJavaFieldTypeResVO::getJavaType, Function.identity(), (a, b) -> a))
                .values());
        return Result.ok(javaFieldTypeResVOS);
    }

}
