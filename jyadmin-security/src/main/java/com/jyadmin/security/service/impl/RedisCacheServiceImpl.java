package com.jyadmin.security.service.impl;

import com.jyadmin.config.properties.JyAuthProperties;
import com.jyadmin.security.service.CacheService;
import com.jyadmin.domain.UserCacheInfo;
import com.jyadmin.util.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-05-10 23:23 <br>
 * @description: RedisCacheServiceImpl <br>
 */
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
        return (UserCacheInfo) redisUtil.getValue(key);
    }

    @Override
    public boolean remove(String username) {
        String key = jyAuthProperties.getAuthUserPrefix() + ":" + username;
        redisUtil.delete(key);
        return true;
    }
}
