package com.jyadmin.system.config.detail.service;

import com.jyadmin.system.config.detail.domain.ConfigDetail;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 13360
* @description 针对表【sys_config_detail(系统配置信息)】的数据库操作Service
* @createDate 2022-11-02 01:35:30
*/
public interface ConfigDetailService extends IService<ConfigDetail> {

    /**
     * 根据Code 获取当前配置的值
     * @param configDetail 配置
     * @param code code
     * @return value
     */
    String getValueByCode(ConfigDetail configDetail, String code);

}
