package com.jyadmin.security.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jyadmin.config.properties.JyAuthProperties;
import com.jyadmin.consts.ResultStatus;
import com.jyadmin.exception.ApiException;
import com.jyadmin.security.service.CacheService;
import com.jyadmin.domain.UserCacheInfo;
import com.jyadmin.util.RedisUtil;
import com.jyadmin.util.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-05-10 23:23 <br>
 * @description: RedisCacheServiceImpl <br>
 */
@Slf4j
@Service("redisCacheService")
public class RedisCacheServiceImpl implements CacheService {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private JyAuthProperties jyAuthProperties;

    @Override
    public boolean save(UserCacheInfo userCacheInfo) {
        String key = jyAuthProperties.getAuthUserPrefix() + ":" + userCacheInfo.getUsername();
        return redisUtil.setValue(key, userCacheInfo, jyAuthProperties.getAuthUserExpiration(), TimeUnit.SECONDS);
    }

    @Override
    public boolean isExist(String username) {
        String key = jyAuthProperties.getAuthUserPrefix() + ":" + username;
        return redisUtil.exists(key);
    }

    @Override
    public UserCacheInfo get(String username) {
        String key = jyAuthProperties.getAuthUserPrefix() + ":" + username;
        if (!this.isExist(username)) return null;
        try {
            Object value = redisUtil.getValue(key);
            return new ObjectMapper().readValue(JSON.toJSONString(value), UserCacheInfo.class);
        } catch (JsonProcessingException e) {
            log.error(ThrowableUtil.getStackTrace(e));
            throw new ApiException(e, ResultStatus.FAIL);
        }
    }

    @Override
    public boolean remove(String username) {
        String key = jyAuthProperties.getAuthUserPrefix() + ":" + username;
        redisUtil.delete(key);
        return true;
    }

    @Override
    public boolean lockUser(String username) {
        String key = jyAuthProperties.getAuthUserLockPrefix() + ":" + username;
        return redisUtil.setValue(key, 1, jyAuthProperties.getAuthUserLockExpiration(), TimeUnit.SECONDS);
    }

    @Override
    public boolean isLocked(String username) {
        String key = jyAuthProperties.getAuthUserLockPrefix() + ":" + username;
        return redisUtil.exists(key);
    }
}
