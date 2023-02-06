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
@ConfigurationProperties("jyadmin.auth.jwt")
@Component
@Data
public class JyJwtProperties {

    /**
     * accessToken过期时间
     * 默认：1h
     */
    private Long accessTokenExpiration= 1 * 60 * 60L;

    /**
     * refreshToken过期时间
     * 默认：12h
     */
    private Long refreshTokenExpiration = 12 * 60 * 60L;

    /**
     * 系统加密密钥
     */
    private String secretKey = "jyadmin";

}
