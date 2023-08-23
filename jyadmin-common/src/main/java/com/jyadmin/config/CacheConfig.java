package com.jyadmin.config;

import java.util.List;
import java.util.concurrent.TimeUnit;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.jyadmin.config.properties.JyCacheProperties;
import com.ulisesbocchio.jasyptspringboot.annotation.ConditionalOnMissingBean;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 缓存配置类 Caffeine
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-05-31 11:15 <br>
 * @description: CacheConfig <br>
 */
@Configuration
public class CacheConfig extends CachingConfigurerSupport {

    @Resource
    private JyCacheProperties cacheProperties;

    @Resource
    private ApplicationContext applicationContext;

    // 初始的缓存空间大小 100条
    private static Integer DEFAULT_CACHE_INITIAL_CAPACITY = 100;
    // 最大缓存数量 1000条
    private static Integer DEFAULT_CACHE_MAX_SIZE = 1000;
    // 缓存过期时间 10s
    private static Integer DEFAULT_CACHE_EXPIRE_AFTER_WRITE = 10;

    /**
     * 公共默认缓存管理器
     * @return /
     */
    @Primary
    @Bean("commonCacheManager")
    public CacheManager commonCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                // 初始的缓存空间大小
                .initialCapacity(DEFAULT_CACHE_INITIAL_CAPACITY)
                // 缓存的最大条数
                .maximumSize(DEFAULT_CACHE_MAX_SIZE)
                // 设置最后一次写入或访问后10s后过期
                .expireAfterWrite(DEFAULT_CACHE_EXPIRE_AFTER_WRITE, TimeUnit.SECONDS));
        return cacheManager;
    }

    /**
     * 批量注册用户自定义缓存管理器
     */
    @Bean
    public void registerUserDefinedCacheManagerBeans() {
        // 获取应用程序上下文的 ConfigurableListableBeanFactory
        ConfigurableListableBeanFactory beanFactory = (ConfigurableListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        List<JyCacheProperties.UserDefinedCacheManager> cacheManagers = cacheProperties.getCacheManagers();
        if (CollectionUtils.isEmpty(cacheManagers)) return;
        for (JyCacheProperties.UserDefinedCacheManager cacheManagerConfig : cacheManagers) {
            CaffeineCacheManager cacheManager = new CaffeineCacheManager();
                    cacheManager.setCaffeine(Caffeine.newBuilder()
                    .initialCapacity(cacheManagerConfig.getInitialCapacity())
                    .maximumSize(cacheManagerConfig.getMaximumSize())
                    .expireAfterWrite(cacheManagerConfig.getExpireAfterWrite(), TimeUnit.SECONDS));
            beanFactory.registerSingleton(cacheManagerConfig.getCacheManagerName(), cacheManager);
        }
    }

}
