package com.jyadmin.system.datadict.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jyadmin.domain.Result;
import com.jyadmin.log.annotation.Log;
import com.jyadmin.system.datadict.domain.SimpleDataDict;
import com.jyadmin.system.datadict.domain.SimpleDataDictDetail;
import com.jyadmin.system.datadict.model.vo.*;
import com.jyadmin.system.datadict.service.SimpleDataDictDetailService;
import com.jyadmin.system.datadict.service.SimpleDataDictService;
import com.jyadmin.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-10-30 16:28 <br>
 * @description: SimpleDataDictDetailController <br>
 */
@Slf4j
@Api(value = "通用数据字典详情详情", tags = {"系统：通用数据字典详情接口"})
@RequestMapping("/api/simple-data-dict-detail")
@RestController
public class SimpleDataDictDetailController {

    @Resource
    private SimpleDataDictDetailService simpleDataDictDetailService;
    @Resource
    private SimpleDataDictService simpleDataDictService;

    @Log(title = "系统管理：新增通用数据字典详情", desc = "新增通用数据字典详情")
    @ApiOperation(value = "新增通用数据字典详情", notes = "")
    @PostMapping("/create")
    @PreAuthorize("@jy.check('simple-datadict-detail:create')")
    public Result<Object> doCreate(@RequestBody @Valid SimpleDataDictDetailCreateVO vo) {
        return ResultUtil.toResult(simpleDataDictDetailService.save(BeanUtil.copyProperties(vo, SimpleDataDictDetail.class)));
    }

    @Log(title = "系统管理：更新通用数据字典详情", desc = "更新通用数据字典详情")
    @ApiOperation(value = "更新通用数据字典详情", notes = "")
    @PutMapping("/update")
    @PreAuthorize("@jy.check('simple-datadict-detail:update')")
    public Result<Object> doUpdate(@RequestBody @Valid SimpleDataDictDetailUpdateVO vo) {
        SimpleDataDictDetail simpleDataDictDetail = simpleDataDictDetailService.getById(vo.getId());
        BeanUtil.copyProperties(vo, simpleDataDictDetail);
        return ResultUtil.toResult(simpleDataDictDetailService.updateById(simpleDataDictDetail));
    }

    @Log(title = "系统管理：删除通用数据字典详情", desc = "删除通用数据字典详情")
    @ApiOperation(value = "删除通用数据字典详情", notes = "")
    @DeleteMapping("/remove")
    @PreAuthorize("@jy.check('simple-datadict-detail:remove')")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(simpleDataDictDetailService.removeByIds(ids));
    }

    @ApiOperation(value = "根据ID查找通用数据字典详情信息", notes = "根据ID查找通用数据字典详情信息")
    @GetMapping("/query/{id}")
    @PreAuthorize("@jy.check('simple-datadict-detail:queryById')")
    public Result<Object> doQueryById(@PathVariable String id) {
        return Result.ok(simpleDataDictDetailService.getById(id));
    }

    @ApiOperation(value = "列表查询通用数据字典详情信息", notes = "列表查询通用数据字典详情信息")
    @GetMapping("/list")
    @PreAuthorize("@jy.check('simple-datadict-detail:list')")
    public Result<List<SimpleDataDictDetail>> doQueryList(SimpleDataDictDetailQueryVO vo) {
        return Result.ok(simpleDataDictDetailService.list(new LambdaQueryWrapper<SimpleDataDictDetail>()
                .eq(StringUtils.isNotBlank(vo.getDataDictId()), SimpleDataDictDetail::getDataDictId, vo.getDataDictId())
                .like(StringUtils.isNotBlank(vo.getName()), SimpleDataDictDetail::getName, vo.getName())
                .like(StringUtils.isNotBlank(vo.getCode()), SimpleDataDictDetail::getCode, vo.getCode())
                .orderByDesc(SimpleDataDictDetail::getCreateTime)
        ));
    }

    @ApiOperation(value = "根据Code查询通用数据字典列表", notes = "根据Code查询通用数据字典列表")
    @GetMapping("/queryByCode/{code}")
    @PreAuthorize("@jy.check('simple-datadict-detail:queryByCode')")
    public Result<List<SimpleDataDictDetail>> doQueryByCode(@PathVariable("code") String code) {
        SimpleDataDict dataDict = simpleDataDictService.getOne(new LambdaQueryWrapper<SimpleDataDict>()
                .eq(StringUtils.isNotBlank(code), SimpleDataDict::getCode, code));
        return Result.ok(simpleDataDictDetailService.list(new LambdaQueryWrapper<SimpleDataDictDetail>()
                .eq(StringUtils.isNotBlank(dataDict.getId()), SimpleDataDictDetail::getDataDictId, dataDict.getId())
                .orderByDesc(SimpleDataDictDetail::getCreateTime)
        ));
    }



    
}
