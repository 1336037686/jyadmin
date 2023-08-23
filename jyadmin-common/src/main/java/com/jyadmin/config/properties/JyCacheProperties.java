package com.jyadmin.config.properties;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-08-23 15:24 <br>
 * @description: JyCacheProperties <br>
 */
@Data
@Component
@ConfigurationProperties(prefix = JyConfigItemConstant.CACHE)
public class JyCacheProperties {

    /**
     * 缓存管理配置
     */
    List<UserDefinedCacheManager> cacheManagers = Lists.newArrayList();

    /**
     * 用户自定义缓存管理器
     */
    @Data
    public static class UserDefinedCacheManager {
        /**
         * 缓存管理名称
         */
        private String cacheManagerName;

        /**
         * 初始的缓存空间大小
         */
        private Integer initialCapacity;

        /**
         * 缓存的最大条数
         */
        private Integer maximumSize;

        /**
         * 设置最后一次写入或访问后10s后过期 单位S
         */
        private Integer expireAfterWrite;

    }
}
