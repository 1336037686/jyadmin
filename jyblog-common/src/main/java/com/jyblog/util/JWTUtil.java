package com.jyblog.util;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.signers.JWTSigner;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-13 01:16 <br>
 * @description: TokenUtil <br>
 */
public class JWTUtil {

    private static Long accessTokenExpiration = 1 * 60 * 60 * 1000l; // 1h
    private static Long refreshTokenExpiration = 2 * 60 * 60 * 1000l; // 2h
    private static final String tokenSignKey = "jyadmin";

    public static String createAccessToken(String username) {
        return JWT.create().setSubject(username)
                                    .setExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpiration))
                                    .setSigner("HS256", tokenSignKey.getBytes(StandardCharsets.UTF_8))
                                    .sign();
    }

    public static String createRefreshToken(String username) {
        return JWT.create()
                .setSubject(username)
                .setExpiresAt(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .setSigner("HS256", tokenSignKey.getBytes(StandardCharsets.UTF_8))
                .sign();
    }

    public static String parseToken(String token) {
        return JWT.create()
                .setKey(tokenSignKey.getBytes(StandardCharsets.UTF_8))
                .parse(token).getPayload(JWT.SUBJECT).toString();
    }

    public static boolean verify(String token) {
        return JWT.of(token).setKey(tokenSignKey.getBytes(StandardCharsets.UTF_8)).verify();
    }

}
