package com.jyadmin.system.config.module.domain;

import com.jyadmin.system.config.detail.domain.ConfigDetail;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 配置包装类（多个）
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-01-23 01:32 <br>
 * @description: MultiModuleConfigWrapper <br>
 */
@Data
@Accessors(chain = true)
public class MultiModuleConfigWrapper {

    /**
     * 配置描述
     */
    private ModuleConfig config;

    /**
     * 配置详情
     */
    private List<ConfigDetail> configDetails;

}
