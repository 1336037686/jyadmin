package com.jyadmin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis配置
 * @description: JyRedisConfig <br>
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-05-04 03:01 <br>
 */
@Configuration
public class RedisConfig {

    /**
     * 配置redis序列化与反序列化设置
     */
    @Bean
    public RedisTemplate redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        // 配置 ObjectMapper
        ObjectMapper objectMapper = buildObjectMapper();
        // 设置 key 的序列化器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // 设置 value 的序列化器为自定义的序列化器，并将自定义的 ObjectMapper 对象传递给它
        redisTemplate.setValueSerializer(new CustomJsonRedisSerializer(objectMapper));
        // 设置 hash key 的序列化器
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // 设置 hash value 的序列化器为自定义的序列化器，并将自定义的 ObjectMapper 对象传递给它
        redisTemplate.setHashValueSerializer(new CustomJsonRedisSerializer(objectMapper));
        // 设置 Redis 连接工厂
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }

    /**
     * 自定义JSON序列化器
     */
    public static class CustomJsonRedisSerializer extends GenericJackson2JsonRedisSerializer {
        public CustomJsonRedisSerializer(ObjectMapper objectMapper) {
            super(objectMapper);
        }
    }

    /**
     * 配置一个 ObjectMapper Bean 来启用 Jackson 序列化和反序列化 Java 8 中的日期时间类型
     */
    public ObjectMapper buildObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }

}
