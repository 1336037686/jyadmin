package com.jyadmin.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 流量控制配置
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-07-07 00:00 <br>
 * @description: JyLimitProperties <br>
 */
@ConfigurationProperties(prefix = JyConfigItemConstant.RATE_LIMIT)
@Data
@Component
public class JyRateLimitProperties {

    /**
     * 自定义限制类型
     */
    public static final String LIMIT_TYPE_CUSTOMER = "CUSTOMER";

    /**
     * IP限制类型
     */
    public static final String LIMIT_TYPE_IP = "IP";


    /**
     * 是否启用
     */
    private Boolean enabled = false;

    /**
     * 缓存前缀
     */
    private String prefix = "jyadmin_ratelimit";

    /**
     * 默认限制间隔时间
     * 1s
     */
    private Integer defaultPeriod = 1;

    /**
     * 默认限制访问次数
     * 1
     */
    private Integer defaultCount = 1;

    /**
     * 限制类型
     */
    private String limitType = LIMIT_TYPE_CUSTOMER;

//    /**
//     * 作用范围
//     */
//    private String area = "";


}
