package com.jyadmin.config.properties;

import cn.hutool.core.util.ArrayUtil;
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
     * 放行所有接口
     */
    private Boolean permitAll = false;

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
     * // Swagger-UI
     * ...
     */
    private List<String> ignoreUrls = new ArrayList<>();

    /**
     * 获取白名单URL
     * @return /
     */
    public String[] getIgnoreUrls() {
        String[] configIgnoreUrls = this.ignoreUrls.stream().toArray(String[]::new);
        String[] commonIgnoreUrls = getCommonIgnoreUrls();
        return ArrayUtil.addAll(configIgnoreUrls, commonIgnoreUrls);
    }

    /**
     * 公共白名单地址
     * 包括Swagger路径， Knife路径，druid路径
     * @return /
     */
    public String[] getCommonIgnoreUrls() {
        return new String[] {
                "/static/**",
                "/doc.html",
                "/swagger-ui.html",
                "/v2/**",
                "/v2/api-docs",
                "/swagger-resources/configuration/ui",
                "/swagger-resources",
                "/swagger-resources/configuration/security",
                "/swagger-resources/**",
                "/webjars/**",
                "/druid/**"
        };
    }

}
