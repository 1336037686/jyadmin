package com.jyadmin.generate.controller;

import cn.hutool.core.bean.BeanUtil;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.generate.model.vo.TableOptionRespVO;
import com.jyadmin.generate.model.vo.TableQueryReqVO;

import com.jyadmin.generate.model.vo.UserConfigReqVO;
import com.jyadmin.generate.service.CodeGenerateService;
import com.jyadmin.util.PageUtil;
import com.jyadmin.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

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

    @ApiOperation(value = "导入数据库表", notes = "")
    @PostMapping("/create/table/{tableName}")
    public Result<Object> doCreate(@PathVariable("tableName") String tableName) {
        return ResultUtil.toResult(codeGenerateService.saveTable(tableName));
    }

    @ApiOperation(value = "同步数据库表", notes = "")
    @PutMapping("/update/table/{tableId}")
    public Result<Object> doUpdate(@PathVariable("tableId") String tableId) {
        return ResultUtil.toResult(codeGenerateService.updateTable(tableId));
    }

    @ApiOperation(value = "删除数据库表", notes = "")
    @DeleteMapping("/remove/table")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(codeGenerateService.removeByIds(ids));
    }

    @ApiOperation(value = "更新数据库表配置", notes = "")
    @PutMapping("/update/table-config")
    public Result<Object> doUpdateTableConfig(@RequestBody @Valid UserConfigReqVO vo) {
        return ResultUtil.toResult(codeGenerateService.updateTableConfig(vo));
    }

    @ApiOperation(value = "分页获取数据库表列表", notes = "")
    @GetMapping("/query-table-exist/{tableId}")
    public Result<Object> doQueryTableExist(@PathVariable("tableId") String tableId) {
        return Result.ok(codeGenerateService.getTableExist(tableId));
    }

    @ApiOperation(value = "分页获取数据库表列表", notes = "")
    @GetMapping("/query")
    public PageResult<TableOptionRespVO> doQueryTableOptionsPage(TableQueryReqVO vo) {
        List<TableOptionRespVO> list = codeGenerateService.getTableOptionsList(vo);
        return PageUtil.toPageResult(list, vo);
    }

    @ApiOperation(value = "代码生成接口", notes = "")
    @GetMapping("/generate/{tableId}")
    public Result<Object> doGenerateCode(@PathVariable("tableId") String tableId) {
        return ResultUtil.toResult(codeGenerateService.generateCode(tableId));
    }


}
