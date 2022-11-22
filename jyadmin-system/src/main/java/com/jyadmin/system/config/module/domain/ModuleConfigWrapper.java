package com.jyadmin.system.config.module.domain;

import com.jyadmin.system.config.detail.domain.ConfigDetail;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 配置包装类
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-19 02:20 <br>
 * @description: FileConfigWrapper <br>
 */
@Data
@Accessors(chain = true)
public class ModuleConfigWrapper {

    /**
     * 配置描述
     */
    private ModuleConfig config;

    /**
     * 配置详情
     */
    private ConfigDetail configDetail;

}
