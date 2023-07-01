package com.jyadmin.system.config.module.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyadmin.system.config.detail.domain.ConfigDetail;
import com.jyadmin.system.config.detail.domain.ConfigDetailJsonModel;
import com.jyadmin.system.config.detail.service.ConfigDetailService;
import com.jyadmin.system.config.module.domain.ModuleConfig;
import com.jyadmin.system.config.module.domain.ModuleConfigWrapper;
import com.jyadmin.system.config.module.domain.MultiModuleConfigWrapper;
import com.jyadmin.system.config.module.mapper.ModuleConfigMapper;
import com.jyadmin.system.config.module.service.ModuleConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 13360
* @description 针对表【sys_module_config(系统基础配置（各个模块单独配置）)】的数据库操作Service实现
* @createDate 2022-11-22 11:29:17
*/
@Service
public class ModuleConfigServiceImpl extends ServiceImpl<ModuleConfigMapper, ModuleConfig>
    implements ModuleConfigService{

    @Resource
    private ConfigDetailService configDetailService;

    /**
     * 获取当前的配置
     * @return ConfigDetail 配置详情
     */
    @Override
    public ModuleConfigWrapper getEnableConfigDetail(Long moduleId) {
        ModuleConfig fileConfig = this.getById(moduleId);
        ConfigDetail enabledConfig = this.configDetailService.getOne(new LambdaQueryWrapper<ConfigDetail>().eq(true, ConfigDetail::getCode, fileConfig.getConfig()));
        enabledConfig.setJsonObjs(StringUtils.isBlank(enabledConfig.getData()) ? new ArrayList<>() :
                JSON.parseArray(enabledConfig.getData(), ConfigDetailJsonModel.class).stream()
                        .sorted(Comparator.comparing(ConfigDetailJsonModel::getSort)).collect(Collectors.toList()));
        return new ModuleConfigWrapper().setConfig(fileConfig).setConfigDetail(enabledConfig);
    }

    @Override
    public MultiModuleConfigWrapper getEnableMultiConfigDetail(Long moduleId) {
        ModuleConfig fileConfig = this.getById(moduleId);
        List<ConfigDetail> enabledConfigs = this.configDetailService.list(new LambdaQueryWrapper<ConfigDetail>().likeRight(true, ConfigDetail::getCode, fileConfig.getConfig()));
        enabledConfigs.stream().peek(x -> {
            x.setJsonObjs(StringUtils.isBlank(x.getData()) ? new ArrayList<>() :
                    JSON.parseArray(x.getData(), ConfigDetailJsonModel.class).stream()
                            .sorted(Comparator.comparing(ConfigDetailJsonModel::getSort)).collect(Collectors.toList()));
        }).collect(Collectors.toList());

        return new MultiModuleConfigWrapper().setConfig(fileConfig).setConfigDetails(enabledConfigs);
    }


}




