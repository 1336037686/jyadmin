package com.jyadmin.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统SpringSecurity配置信息
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-05-08 20:10 <br>
 * @description: JySecurityConfig <br>
 */
@ConfigurationProperties("jyadmin.safe.security")
@Component
@Data
public class JySecurityProperties {

    /**
     * 白名单地址配置
     *
     * // 登录
     * IGNORE_URL.add("/api/auth/login");
     * // 注册
     * IGNORE_URL.add("/api/auth/register");
     * // 验证码
     * IGNORE_URL.add("/api/auth/captcha/{uniqueId}");
     * // 登陆续期
     * IGNORE_URL.add("/api/auth/refreshToken");
     */
    private List<String> ignoreUrls = new ArrayList<>();

    public String[] getIgnoreUrls() {
        return this.ignoreUrls.stream().toArray(String[]::new);
    }

}
