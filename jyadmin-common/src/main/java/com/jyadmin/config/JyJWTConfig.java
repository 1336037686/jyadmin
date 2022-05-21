package com.jyadmin.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * JWT配置
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-29 21:31 <br>
 * @description: JyJWTConfig <br>
 */
@Component("jwtConfig")
@Data
public class JyJWTConfig {

    // accessToken过期时间
    @Value("${jwt.accessTokenExpiration}")
    private Long accessTokenExpiration; // 1 * 60 * 60l; // 1h

    // refreshToken过期时间
    @Value("${jwt.refreshTokenExpiration}")
    private Long refreshTokenExpiration;// 2 * 60 * 60l; // 2h

    // key
    @Value("${jwt.tokenSignKey}")
    private String tokenSignKey;

    @Value("${jwt.loginUserKey}")
    private String loginUserKey;

    @Value("${jwt.verificationCodeKey}")
    private String verificationCodeKey;

}
