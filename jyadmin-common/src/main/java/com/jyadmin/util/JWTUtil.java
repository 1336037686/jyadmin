package com.jyadmin.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import com.jyadmin.config.properties.JyJwtProperties;
import com.jyadmin.consts.GlobalConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-13 01:16 <br>
 * @description: TokenUtil <br>
 */
@Slf4j
public class JWTUtil {

    /**
     * 创建AccessToken
     * @param username /
     * @return /
     */
    public static String createAccessToken(String username) {
        JyJwtProperties jwtConfig = SpringUtil.getBean(JyJwtProperties.class);

        DateTime now = DateTime.now();
        DateTime newTime = now.offsetNew(DateField.SECOND, jwtConfig.getAccessTokenExpiration().intValue());

        Map<String,Object> payload = new HashMap<>();
        //签发时间
        payload.put(JWTPayload.ISSUED_AT, now);
        //过期时间
        payload.put(JWTPayload.EXPIRES_AT, newTime);
        //生效时间
        payload.put(JWTPayload.NOT_BEFORE, now);
        //载荷
        payload.put(GlobalConstants.SYS_JWT_TOKEN_PAYLOAD_USERNAME, username);
        return cn.hutool.jwt.JWTUtil.createToken(payload, jwtConfig.getSecretKey().getBytes());
    }

    /**
     * 创建RefreshToken
     * @param username /
     * @return /
     */
    public static String createRefreshToken(String username) {
        JyJwtProperties jwtConfig = SpringUtil.getBean(JyJwtProperties.class);

        DateTime now = DateTime.now();
        DateTime newTime = now.offsetNew(DateField.SECOND, jwtConfig.getRefreshTokenExpiration().intValue());

        Map<String,Object> payload = new HashMap<String,Object>();
        //签发时间
        payload.put(JWTPayload.ISSUED_AT, now);
        //过期时间
        payload.put(JWTPayload.EXPIRES_AT, newTime);
        //生效时间
        payload.put(JWTPayload.NOT_BEFORE, now);
        //载荷
        payload.put(GlobalConstants.SYS_JWT_TOKEN_PAYLOAD_USERNAME, username);
        return cn.hutool.jwt.JWTUtil.createToken(payload, jwtConfig.getSecretKey().getBytes());
    }

    /**
     * 解析Token
     * @param token /
     * @return /
     */
    public static String parseToken(String token) {
        JyJwtProperties jwtConfig = SpringUtil.getBean(JyJwtProperties.class);
        JWT jwt = cn.hutool.jwt.JWTUtil.parseToken(token);
        Object username = jwt.setKey(jwtConfig.getSecretKey().getBytes()).getPayload(GlobalConstants.SYS_JWT_TOKEN_PAYLOAD_USERNAME);
        return username.toString();
    }

    /**
     * 校验Token
     * @param token /
     * @return /
     */
    public static boolean verify(String token) {
        if (StringUtils.isBlank(token)) return false;
        JyJwtProperties jwtConfig = SpringUtil.getBean(JyJwtProperties.class);
        JWT jwt = cn.hutool.jwt.JWTUtil.parseToken(token);
        jwt.setKey(jwtConfig.getSecretKey().getBytes());
        return jwt.validate(0) && jwt.verify();
    }

    /**
     * 校验Token是否过期
     * @param token /
     * @return /
     */
    public static boolean validateTime(String token) {
        if (StringUtils.isBlank(token)) return false;
        JyJwtProperties jwtConfig = SpringUtil.getBean(JyJwtProperties.class);
        JWT jwt = cn.hutool.jwt.JWTUtil.parseToken(token);
        return jwt.setKey(jwtConfig.getSecretKey().getBytes()).validate(0);
    }

    /**
     * 校验Token是否有效
     * @param token /
     * @return /
     */
    public static boolean validateLegal(String token) {
        if (StringUtils.isBlank(token)) return false;
        JyJwtProperties jwtConfig = SpringUtil.getBean(JyJwtProperties.class);
        JWT jwt = cn.hutool.jwt.JWTUtil.parseToken(token);
        return jwt.setKey(jwtConfig.getSecretKey().getBytes()).verify();
    }
}
