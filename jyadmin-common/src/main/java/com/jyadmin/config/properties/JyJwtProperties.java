package com.jyadmin.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT配置
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-07-04 22:57 <br>
 * @description: JyJwtProperties <br>
 */
@ConfigurationProperties(prefix = JyConfigItemConstant.AUTH_JWT)
@Component
@Data
public class JyJwtProperties {

    /**
     * accessToken过期时间
     * 默认：2h
     */
    private Long accessTokenExpiration= 2 * 60 * 60L;

    /**
     * refreshToken过期时间
     * 默认：48h
     */
    private Long refreshTokenExpiration = 48 * 60 * 60L;

    /**
     * 系统加密密钥
     */
    private String secretKey = "jyadmin";

}
