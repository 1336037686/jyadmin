package com.jyblog.system.datadict.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyblog.domain.PageResult;
import com.jyblog.domain.Result;
import com.jyblog.system.datadict.domain.SysDataDict;
import com.jyblog.system.datadict.model.dto.SysDataDictQueryDTO;
import com.jyblog.system.datadict.model.vo.SysDataDictCreateNodeVO;
import com.jyblog.system.datadict.model.vo.SysDataDictCreateRootVO;
import com.jyblog.system.datadict.model.vo.SysDataDictQueryVO;
import com.jyblog.system.datadict.model.vo.SysDataDictUpdateVO;
import com.jyblog.system.datadict.service.SysDataDictService;
import com.jyblog.util.PageUtil;
import com.jyblog.util.ResultUtil;
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
@Api(value = "数据字典", tags = {"数据字典接口"})
@RequestMapping("system/data-dict")
@RestController
public class SysDataDictController {

    @Resource
    private SysDataDictService sysDataDictService;

    @ApiOperation(value = "创建根节点", notes = "")
    @PostMapping("/create-root")
    public Result<Object> doCreateRoot(@RequestBody @Valid SysDataDictCreateRootVO vo) {
        SysDataDict sysDataDict = BeanUtil.copyProperties(vo, SysDataDict.class);
        sysDataDict.setIsRoot(1);
        return ResultUtil.toResult(sysDataDictService.save(sysDataDict));
    }

    @ApiOperation(value = "创建子节点", notes = "")
    @PostMapping("/create-node")
    public Result<Object> doCreateNode(@RequestBody @Valid SysDataDictCreateNodeVO vo) {
        SysDataDict sysDataDict = BeanUtil.copyProperties(vo, SysDataDict.class);
        sysDataDict.setIsRoot(0);
        return ResultUtil.toResult(sysDataDictService.save(sysDataDict));
    }

    @ApiOperation(value = "更新节点", notes = "")
    @PutMapping("/update")
    public Result<Object> doUpdate(@RequestBody @Valid SysDataDictUpdateVO vo) {
        SysDataDict sysDataDict = sysDataDictService.getById(vo.getId());
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
    public PageResult<SysDataDict> doQueryPage(SysDataDictQueryVO vo) {
        return PageUtil.toPageResult(this.sysDataDictService.getPage(BeanUtil.copyProperties(vo, SysDataDictQueryDTO.class)));
    }

    @ApiOperation(value = "根据ID查询下属节点", notes = "")
    @GetMapping("/query-child/{id}")
    public Result<Object> doQueryChild(@PathVariable String id) {
        return Result.ok(sysDataDictService.getChildById(id));
    }
}
