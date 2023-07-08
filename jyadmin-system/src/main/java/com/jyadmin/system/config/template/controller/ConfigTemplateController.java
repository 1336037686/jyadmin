package com.jyadmin.system.config.template.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.annotation.RateLimit;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.log.annotation.Log;
import com.jyadmin.system.config.template.domain.ConfigTemplate;
import com.jyadmin.system.config.template.domain.ConfigTemplateJsonModel;
import com.jyadmin.system.config.template.model.vo.ConfigTemplateCreateVO;
import com.jyadmin.system.config.template.model.vo.ConfigTemplateQueryVO;
import com.jyadmin.system.config.template.model.vo.ConfigTemplateUpdateVO;
import com.jyadmin.system.config.template.service.ConfigTemplateService;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 配置模板
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-02 19:10 <br>
 * @description: ConfigTemplateController <br>
 */
@Slf4j
@Api(value = "系统配置模板", tags = "系统：系统模板接口")
@RequestMapping("/api/config-template")
@RestController
public class ConfigTemplateController {

    @Resource
    private ConfigTemplateService configTemplateService;

    @RateLimit
    @Log(title = "系统配置模板：新增模板", desc = "新增模板")
    @ApiOperation(value = "新增模板", notes = "")
    @PostMapping("/create")
    @PreAuthorize("@jy.check('config-template:create')")
    public Result<Object> doCreate(@RequestBody @Valid ConfigTemplateCreateVO vo) {
        return ResultUtil.toResult(configTemplateService.save(BeanUtil.copyProperties(vo, ConfigTemplate.class)));
    }

    @RateLimit
    @Log(title = "系统配置模板：更新模板", desc = "更新模板")
    @ApiOperation(value = "更新模板", notes = "")
    @PutMapping("/update")
    @PreAuthorize("@jy.check('config-template:update')")
    public Result<Object> doUpdate(@RequestBody @Valid ConfigTemplateUpdateVO vo) {
        ConfigTemplate configTemplate = configTemplateService.getById(vo.getId());
        BeanUtil.copyProperties(vo, configTemplate);
        return ResultUtil.toResult(configTemplateService.updateById(configTemplate));
    }

    @RateLimit
    @Log(title = "系统配置模板：删除模板", desc = "删除模板")
    @ApiOperation(value = "删除模板", notes = "")
    @DeleteMapping("/remove")
    @PreAuthorize("@jy.check('config-template:remove')")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(configTemplateService.removeByIds(DataUtil.convertToLongForSet(ids)));
    }

    @ApiOperation(value = "根据ID获取当前模板信息", notes = "")
    @GetMapping("/query/{id}")
    @PreAuthorize("@jy.check('config-template:queryById')")
    public Result<Object> doQueryById(@PathVariable("id") String id) {
        ConfigTemplate configTemplate = configTemplateService.getById(Long.parseLong(id));
        configTemplate.setJsonObjs(
                StringUtils.isBlank(configTemplate.getTemplate()) ? new ArrayList<>() :
                        JSON.parseArray(configTemplate.getTemplate(), ConfigTemplateJsonModel.class)
                                .stream().sorted(Comparator.comparing(ConfigTemplateJsonModel::getSort))
                                .collect(Collectors.toList())
        );
        return Result.ok(configTemplate);
    }

    @ApiOperation(value = "列表查询模板", notes = "")
    @GetMapping("/list")
    @PreAuthorize("@jy.check('config-template:list')")
    public Result<List<ConfigTemplate>> doQueryList(ConfigTemplateQueryVO vo) {

        List<ConfigTemplate> configTemplates = this.configTemplateService.list(new LambdaQueryWrapper<ConfigTemplate>()
                .like(StringUtils.isNotBlank(vo.getName()), ConfigTemplate::getName, vo.getName())
                .like(StringUtils.isNotBlank(vo.getCode()), ConfigTemplate::getCode, vo.getCode())
                .orderByDesc(ConfigTemplate::getCreateTime)
        );
        List<ConfigTemplate> newConfigTemplates = configTemplates.stream()
                .peek(x -> x.setJsonObjs(
                        StringUtils.isBlank(x.getTemplate()) ? new ArrayList<>() :
                                JSON.parseArray(x.getTemplate(), ConfigTemplateJsonModel.class)
                                        .stream().sorted(Comparator.comparing(ConfigTemplateJsonModel::getSort))
                                        .collect(Collectors.toList())
                )).collect(Collectors.toList());
        return Result.ok(newConfigTemplates);
    }

    @ApiOperation(value = "分页查询模板", notes = "")
    @GetMapping("/query")
    @PreAuthorize("@jy.check('config-template:query')")
    public PageResult<ConfigTemplate> doQueryPage(ConfigTemplateQueryVO vo) {
        Page<ConfigTemplate> page = this.configTemplateService.page(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                new LambdaQueryWrapper<ConfigTemplate>()
                        .like(StringUtils.isNotBlank(vo.getName()), ConfigTemplate::getName, vo.getName())
                        .like(StringUtils.isNotBlank(vo.getCode()), ConfigTemplate::getCode, vo.getCode())
                        .orderByDesc(ConfigTemplate::getCreateTime)
        );
        List<ConfigTemplate> configTemplates = page.getRecords().stream().peek(x -> x.setJsonObjs(
                StringUtils.isBlank(x.getTemplate()) ? new ArrayList<>() :
                        JSON.parseArray(x.getTemplate(), ConfigTemplateJsonModel.class)
                                .stream().sorted(Comparator.comparing(ConfigTemplateJsonModel::getSort))
                                .collect(Collectors.toList())
        )).collect(Collectors.toList());
        page.setRecords(configTemplates);
        return PageUtil.toPageResult(page);
    }

}
