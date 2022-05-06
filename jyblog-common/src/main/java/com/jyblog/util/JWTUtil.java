package com.jyblog.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.jwt.JWT;
import com.jyblog.config.JyJWTConfig;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-13 01:16 <br>
 * @description: TokenUtil <br>
 */
public class JWTUtil {

    /**
     * 创建AccessToken
     * @param username
     * @return
     */
    public static String createAccessToken(String username) {
        JyJWTConfig jwtConfig = SpringUtil.getBean(JyJWTConfig.class);
        return JWT.create().setSubject(username)
                                    .setExpiresAt(new Date(DateUtil.date().getTime() + jwtConfig.getAccessTokenExpiration() * 1000))
                                    .setSigner("HS256", jwtConfig.getTokenSignKey().getBytes(StandardCharsets.UTF_8))
                                    .sign();
    }

    /**
     * 创建RefreshToken
     * @param username
     * @return
     */
    public static String createRefreshToken(String username) {
        JyJWTConfig jwtConfig = SpringUtil.getBean(JyJWTConfig.class);
        return JWT.create()
                .setSubject(username)
                .setExpiresAt(new Date(DateUtil.date().getTime() + jwtConfig.getRefreshTokenExpiration() * 1000))
                .setSigner("HS256", jwtConfig.getTokenSignKey().getBytes(StandardCharsets.UTF_8))
                .sign();
    }

    /**
     * 解析Token
     * @param token
     * @return
     */
    public static String parseToken(String token) {
        JyJWTConfig jwtConfig = SpringUtil.getBean(JyJWTConfig.class);
        return JWT.create()
                .setKey(jwtConfig.getTokenSignKey().getBytes(StandardCharsets.UTF_8))
                .parse(token).getPayload(JWT.SUBJECT).toString();
    }

    /**
     * 校验Token
     * @param token
     * @return
     */
    public static boolean verify(String token) {
        JyJWTConfig jwtConfig = SpringUtil.getBean(JyJWTConfig.class);
        return JWT.of(token).setKey(jwtConfig.getTokenSignKey().getBytes(StandardCharsets.UTF_8)).validate(0);
    }
}
