package com.jyblog.security.service.impl;

import com.jyblog.config.JyJWTConfig;
import com.jyblog.security.domain.UserCacheInfo;
import com.jyblog.security.service.CacheService;
import com.jyblog.util.RedisUtil;
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
    private JyJWTConfig jwtConfig;

    @Override
    public boolean save(UserCacheInfo userCacheInfo) {
        String key = jwtConfig.getLoginUserKey() + ":" + userCacheInfo.getUsername();
        return redisUtil.setValue(key, userCacheInfo, jwtConfig.getAccessTokenExpiration(), TimeUnit.SECONDS);
    }

    @Override
    public boolean isExist(String username) {
        String key = jwtConfig.getLoginUserKey() + ":" + username;
        return redisUtil.exists(key);
    }

    @Override
    public UserCacheInfo get(String username) {
        String key = jwtConfig.getLoginUserKey() + ":" + username;
        if (!this.isExist(username)) return null;
        return (UserCacheInfo) redisUtil.getValue(key);
    }

    @Override
    public boolean remove(String username) {
        String key = jwtConfig.getLoginUserKey() + ":" + username;
        redisUtil.delete(key);
        return true;
    }
}
