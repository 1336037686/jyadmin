package com.jyadmin.system.config.detail.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.annotation.RateLimit;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.log.annotation.Log;
import com.jyadmin.system.config.detail.domain.ConfigDetail;
import com.jyadmin.system.config.detail.domain.ConfigDetailJsonModel;
import com.jyadmin.system.config.detail.model.vo.ConfigDetailCreateVO;
import com.jyadmin.system.config.detail.model.vo.ConfigDetailQueryVO;
import com.jyadmin.system.config.detail.model.vo.ConfigDetailUpdateVO;
import com.jyadmin.system.config.detail.service.ConfigDetailService;
import com.jyadmin.util.DataUtil;
import com.jyadmin.util.PageUtil;
import com.jyadmin.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 配置配置信息
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-02 19:10 <br>
 * @description: ConfigTemplateController <br>
 */
@Slf4j
@Api(value = "系统配置信息", tags = "系统：系统配置信息接口")
@RequestMapping("/api/config-detail")
@RestController
public class ConfigDetailController {

    @Resource
    private ConfigDetailService configDetailService;

    @RateLimit
    @Log(title = "系统配置信息：新增配置信息", desc = "新增配置信息")
    @ApiOperation(value = "新增配置信息", notes = "")
    @PostMapping("/create")
    @PreAuthorize("@jy.check('config-detail:create')")
    public Result<Object> doCreate(@RequestBody @Valid ConfigDetailCreateVO vo) {
        return ResultUtil.toResult(configDetailService.save(BeanUtil.copyProperties(vo, ConfigDetail.class)));
    }

    @RateLimit
    @Log(title = "系统配置信息：更新配置信息", desc = "更新配置信息")
    @ApiOperation(value = "更新配置信息", notes = "")
    @PutMapping("/update")
    @PreAuthorize("@jy.check('config-detail:update')")
    public Result<Object> doUpdate(@RequestBody @Valid ConfigDetailUpdateVO vo) {
        ConfigDetail configDetail = configDetailService.getById(vo.getId());
        BeanUtil.copyProperties(vo, configDetail);
        return ResultUtil.toResult(configDetailService.updateById(configDetail));
    }

    @RateLimit
    @Log(title = "系统配置信息：删除配置信息", desc = "删除配置信息")
    @ApiOperation(value = "删除配置信息", notes = "")
    @DeleteMapping("/remove")
    @PreAuthorize("@jy.check('config-detail:remove')")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(configDetailService.removeByIds(DataUtil.convertToLongForSet(ids)));
    }

    @ApiOperation(value = "根据ID获取当前配置信息信息", notes = "")
    @GetMapping("/query/{id}")
    @PreAuthorize("@jy.check('config-detail:queryById')")
    public Result<Object> doQueryById(@PathVariable String id) {
        ConfigDetail configDetail = configDetailService.getById(Long.parseLong(id));
        configDetail.setJsonObjs(
                StringUtils.isBlank(configDetail.getData()) ? new ArrayList<>() :
                        JSON.parseArray(configDetail.getData(), ConfigDetailJsonModel.class)
                                .stream().sorted(Comparator.comparing(ConfigDetailJsonModel::getSort))
                                .collect(Collectors.toList())
        );
        return Result.ok(configDetail);
    }

    @ApiOperation(value = "列表查询配置信息", notes = "")
    @GetMapping("/list")
    @PreAuthorize("@jy.check('config-detail:list')")
    public Result<List<ConfigDetail>> doQueryList(ConfigDetailQueryVO vo) {

        List<ConfigDetail> configDetails = this.configDetailService.list(new LambdaQueryWrapper<ConfigDetail>()
                .eq(Objects.nonNull(vo.getTemplateId()), ConfigDetail::getTemplateId, vo.getTemplateId())
                .like(StringUtils.isNotBlank(vo.getName()), ConfigDetail::getName, vo.getName())
                .like(StringUtils.isNotBlank(vo.getCode()), ConfigDetail::getCode, vo.getCode())
                .orderByDesc(ConfigDetail::getCreateTime)
        );
        List<ConfigDetail> newConfigDetails = configDetails.stream()
                .peek(x -> x.setJsonObjs(
                        StringUtils.isBlank(x.getData()) ? new ArrayList<>() :
                                JSON.parseArray(x.getData(), ConfigDetailJsonModel.class)
                                        .stream().sorted(Comparator.comparing(ConfigDetailJsonModel::getSort))
                                        .collect(Collectors.toList())
                )).collect(Collectors.toList());
        return Result.ok(newConfigDetails);
    }

    @ApiOperation(value = "分页查询配置信息", notes = "")
    @GetMapping("/query")
    @PreAuthorize("@jy.check('config-detail:query')")
    public PageResult<ConfigDetail> doQueryPage(ConfigDetailQueryVO vo) {
        Page<ConfigDetail> page = this.configDetailService.page(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                new LambdaQueryWrapper<ConfigDetail>()
                        .eq(Objects.nonNull(vo.getTemplateId()), ConfigDetail::getTemplateId, vo.getTemplateId())
                        .like(StringUtils.isNotBlank(vo.getName()), ConfigDetail::getName, vo.getName())
                        .like(StringUtils.isNotBlank(vo.getCode()), ConfigDetail::getCode, vo.getCode())
                        .orderByDesc(ConfigDetail::getCreateTime)
        );
        List<ConfigDetail> configDetails = page.getRecords().stream().peek(x -> x.setJsonObjs(
                StringUtils.isBlank(x.getData()) ? new ArrayList<>() :
                        JSON.parseArray(x.getData(), ConfigDetailJsonModel.class)
                                .stream().sorted(Comparator.comparing(ConfigDetailJsonModel::getSort))
                                .collect(Collectors.toList())
        )).collect(Collectors.toList());
        page.setRecords(configDetails);
        return PageUtil.toPageResult(page);
    }

}
