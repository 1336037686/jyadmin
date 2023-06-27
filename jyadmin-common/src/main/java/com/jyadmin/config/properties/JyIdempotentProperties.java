package com.jyadmin.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 接口幂等实现配置
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-07-09 00:18 <br>
 * @description: JyIdempotentProperties <br>
 */
@Data
@Component
@ConfigurationProperties(prefix = JyConfigItemConstant.IDEMPOTENT)
public class JyIdempotentProperties {

    /**
     * 是否启用
     */
    private Boolean enabled = false;

    /**
     * 缓存前缀
     */
    private String prefix = "jyadmin_idempotent";

    /**
     * 默认过期时间
     * 30min = 30 * 60s
     */
    private Integer defaultPeriod = 30 * 60;

    /**
     * 缓存Key默认存放值
     */
    private String defaultValue = "1";


}
