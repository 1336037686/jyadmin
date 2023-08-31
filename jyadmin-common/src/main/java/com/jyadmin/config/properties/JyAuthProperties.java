package com.jyadmin.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 认证授权配置
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-07-04 22:46 <br>
 * @description: JyAuthProperties <br>
 */
@ConfigurationProperties(prefix = JyConfigItemConstant.AUTH)
@Component
@Data
public class JyAuthProperties {

    /**
     * 登录尝试次数
     */
    private Integer authloginAttempts = 5;

    /**
     * 登录用户缓存前缀
     */
    private String authUserPrefix = "auth_user";

    /**
     * 认证用户缓存过期时间
     * 该时间需要与 RefreshToken 一致
     * 默认：48h = RefreshToken过期时间
     */
    private Long authUserExpiration = 48 * 60 * 60L;

    /**
     * 验证码缓存前缀
     */
    private String verificationCodePrefix = "verification_code";

    /**
     * 验证码缓存过期时间
     * 默认：5min
     */
    private Long verificationCodeExpiration = 5 * 60L;

    /**
     * 账号锁定缓存前缀
     */
    private String authUserLockPrefix = "auth_user_lock";

    /**
     * 账号锁定缓存过期时间
     * 默认：60min
     */
    private Long authUserLockExpiration = 60 * 60L;

}
