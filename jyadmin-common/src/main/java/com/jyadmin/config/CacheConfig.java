package com.jyadmin.config;

import java.util.concurrent.TimeUnit;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 缓存配置类 Caffeine
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-05-31 11:15 <br>
 * @description: CacheConfig <br>
 */
@Configuration
public class CacheConfig extends CachingConfigurerSupport {

    // 初始的缓存空间大小 100条
    private static Integer DEFAULT_CACHE_INITIAL_CAPACITY = 100;
    // 最大缓存数量 1000条
    private static Integer DEFAULT_CACHE_MAX_SIZE = 1000;
    // 缓存过期时间 10s
    private static Integer DEFAULT_CACHE_EXPIRE_AFTER_WRITE = 10;

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeineCacheBuilder());
        return cacheManager;
    }

    private Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                // 初始的缓存空间大小
                .initialCapacity(DEFAULT_CACHE_INITIAL_CAPACITY)
                // 缓存的最大条数
                .maximumSize(DEFAULT_CACHE_MAX_SIZE)
                // 设置最后一次写入或访问后10s后过期
                .expireAfterWrite(DEFAULT_CACHE_EXPIRE_AFTER_WRITE, TimeUnit.SECONDS);
    }
}
