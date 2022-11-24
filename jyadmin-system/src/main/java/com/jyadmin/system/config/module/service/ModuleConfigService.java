package com.jyadmin.system.config.module.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jyadmin.system.config.module.domain.ModuleConfig;
import com.jyadmin.system.config.module.domain.ModuleConfigWrapper;

/**
* @author 13360
* @description 针对表【sys_module_config(系统基础配置（各个模块单独配置）)】的数据库操作Service
* @createDate 2022-11-22 11:29:17
*/
public interface ModuleConfigService extends IService<ModuleConfig> {

    /**
     * 获取当前的附件配置
     * @return ModuleConfigWrapper 配置包装类
     */
    ModuleConfigWrapper getEnableConfigDetail(String moduleId);

}
