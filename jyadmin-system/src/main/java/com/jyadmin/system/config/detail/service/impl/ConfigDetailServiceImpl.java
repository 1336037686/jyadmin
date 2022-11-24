package com.jyadmin.system.config.detail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyadmin.system.config.detail.domain.ConfigDetail;
import com.jyadmin.system.config.detail.domain.ConfigDetailJsonModel;
import com.jyadmin.system.config.detail.service.ConfigDetailService;
import com.jyadmin.system.config.detail.mapper.ConfigDetailMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
* @author 13360
* @description 针对表【sys_config_detail(系统配置信息)】的数据库操作Service实现
* @createDate 2022-11-02 01:35:30
*/
@Service
public class ConfigDetailServiceImpl extends ServiceImpl<ConfigDetailMapper, ConfigDetail>
    implements ConfigDetailService{

    @Override
    public String getValueByCode(ConfigDetail configDetail, String code) {
        List<ConfigDetailJsonModel> configDetails = configDetail.getJsonObjs();
        ConfigDetailJsonModel config = configDetails.stream().filter(x -> code.equals(x.getCode())).findFirst().orElse(null);
        Assert.notNull(config, "不存在当前配置！");
        return config.getValue();
    }
}




