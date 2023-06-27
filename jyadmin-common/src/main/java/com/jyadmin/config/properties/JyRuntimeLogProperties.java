package com.jyadmin.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 实时运行日志
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-06-04 09:51 <br>
 * @description: JyRuntimeLogProperties <br>
 */
@ConfigurationProperties(prefix = JyConfigItemConstant.LOG_RUNTIME_LOG)
@Component
@Data
public class JyRuntimeLogProperties {

    /**
     * 基础路径
     */
    private String basePath;

}
