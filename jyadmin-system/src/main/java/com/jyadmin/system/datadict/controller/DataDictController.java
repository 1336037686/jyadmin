package com.jyadmin.system.datadict.controller;

import cn.hutool.core.bean.BeanUtil;
import com.jyadmin.util.PageUtil;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.system.datadict.domain.DataDict;
import com.jyadmin.system.datadict.model.dto.DataDictQueryDTO;
import com.jyadmin.system.datadict.model.vo.DataDictCreateNodeVO;
import com.jyadmin.system.datadict.model.vo.DataDictCreateRootVO;
import com.jyadmin.system.datadict.model.vo.DataDictQueryVO;
import com.jyadmin.system.datadict.model.vo.DataDictUpdateVO;
import com.jyadmin.system.datadict.service.DataDictService;
import com.jyadmin.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Set;

/**
 * 数据字典
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-07 21:41 <br>
 * @description: SysDataDictController <br>
 */
@Slf4j
@Api(value = "数据字典", tags = {"系统：数据字典接口"})
@RequestMapping("/api/data-dict")
@RestController
public class DataDictController {

    @Resource
    private DataDictService sysDataDictService;

    @ApiOperation(value = "创建根节点", notes = "")
    @PostMapping("/create-root")
    public Result<Object> doCreateRoot(@RequestBody @Valid DataDictCreateRootVO vo) {
        DataDict sysDataDict = BeanUtil.copyProperties(vo, DataDict.class);
        sysDataDict.setIsRoot(1);
        return ResultUtil.toResult(sysDataDictService.save(sysDataDict));
    }

    @ApiOperation(value = "创建子节点", notes = "")
    @PostMapping("/create-node")
    public Result<Object> doCreateNode(@RequestBody @Valid DataDictCreateNodeVO vo) {
        DataDict sysDataDict = BeanUtil.copyProperties(vo, DataDict.class);
        sysDataDict.setIsRoot(0);
        return ResultUtil.toResult(sysDataDictService.save(sysDataDict));
    }

    @ApiOperation(value = "更新节点", notes = "")
    @PutMapping("/update")
    public Result<Object> doUpdate(@RequestBody @Valid DataDictUpdateVO vo) {
        DataDict sysDataDict = sysDataDictService.getById(vo.getId());
        BeanUtil.copyProperties(vo, sysDataDict);
        return ResultUtil.toResult(sysDataDictService.updateById(sysDataDict));
    }

    @ApiOperation(value = "删除节点", notes = "")
    @DeleteMapping("/remove")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(sysDataDictService.removeByIds(ids));
    }

    @ApiOperation(value = "根据ID获取当前节点信息", notes = "")
    @GetMapping("/query/{id}")
    public Result<Object> doQueryById(@PathVariable String id) {
        return Result.ok(sysDataDictService.getById(id));
    }

    @ApiOperation(value = "分页查询字典", notes = "")
    @GetMapping("/query")
    public PageResult<DataDict> doQueryPage(DataDictQueryVO vo) {
        return PageUtil.toPageResult(this.sysDataDictService.getPage(BeanUtil.copyProperties(vo, DataDictQueryDTO.class)));
    }

    @ApiOperation(value = "根据ID查询下属节点", notes = "")
    @GetMapping("/query-child/{id}")
    public Result<Object> doQueryChild(@PathVariable String id) {
        return Result.ok(sysDataDictService.getChildById(id));
    }
}
