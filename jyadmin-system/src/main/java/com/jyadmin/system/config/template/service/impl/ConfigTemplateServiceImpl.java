package com.jyadmin.system.config.template.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyadmin.system.config.template.domain.ConfigTemplate;
import com.jyadmin.system.config.template.service.ConfigTemplateService;
import com.jyadmin.system.config.template.mapper.ConfigTemplateMapper;
import org.springframework.stereotype.Service;

/**
* @author 13360
* @description 针对表【sys_config_template(系统配置模板)】的数据库操作Service实现
* @createDate 2022-11-02 01:38:46
*/
@Service
public class ConfigTemplateServiceImpl extends ServiceImpl<ConfigTemplateMapper, ConfigTemplate>
    implements ConfigTemplateService{

}




