package com.jyadmin.security.config;

import java.util.ArrayList;
import java.util.List;

/**
 * 白名单
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-05-12 22:58 <br>
 * @description: JyIgnoreUrlConfig <br>
 */
public class JyIgnoreUrlConfig {

    private static List<String> IGNORE_URL = new ArrayList<>();

    static {
        // 登录
        IGNORE_URL.add("/api/auth/login");
        // 注册
        IGNORE_URL.add("/api/auth/register");
        // 验证码
        IGNORE_URL.add("/api/auth/captcha/{uniqueId}");
        // 登陆续期
        IGNORE_URL.add("/api/auth/refreshToken");
    }

    public static String[] getIgnoreUrls() {
        return IGNORE_URL.stream().toArray(String[]::new);
    }
}
