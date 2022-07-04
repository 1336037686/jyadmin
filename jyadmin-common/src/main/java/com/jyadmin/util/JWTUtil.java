package com.jyadmin.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.jwt.JWT;
import com.jyadmin.config.properties.JyJwtProperties;

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
        JyJwtProperties jwtConfig = SpringUtil.getBean(JyJwtProperties.class);
        return JWT.create().setSubject(username)
                                    .setExpiresAt(new Date(DateUtil.date().getTime() + jwtConfig.getAccessTokenExpiration() * 1000))
                                    .setSigner("HS256", jwtConfig.getSecretKey().getBytes(StandardCharsets.UTF_8))
                                    .sign();
    }

    /**
     * 创建RefreshToken
     * @param username
     * @return
     */
    public static String createRefreshToken(String username) {
        JyJwtProperties jwtConfig = SpringUtil.getBean(JyJwtProperties.class);
        return JWT.create()
                .setSubject(username)
                .setExpiresAt(new Date(DateUtil.date().getTime() + jwtConfig.getRefreshTokenExpiration() * 1000))
                .setSigner("HS256", jwtConfig.getSecretKey().getBytes(StandardCharsets.UTF_8))
                .sign();
    }

    /**
     * 解析Token
     * @param token
     * @return
     */
    public static String parseToken(String token) {
        JyJwtProperties jwtConfig = SpringUtil.getBean(JyJwtProperties.class);
        return JWT.create()
                .setKey(jwtConfig.getSecretKey().getBytes(StandardCharsets.UTF_8))
                .parse(token).getPayload(JWT.SUBJECT).toString();
    }

    /**
     * 校验Token
     * @param token
     * @return
     */
    public static boolean verify(String token) {
        JyJwtProperties jwtConfig = SpringUtil.getBean(JyJwtProperties.class);
        return JWT.of(token).setKey(jwtConfig.getSecretKey().getBytes(StandardCharsets.UTF_8)).validate(0);
    }
}
