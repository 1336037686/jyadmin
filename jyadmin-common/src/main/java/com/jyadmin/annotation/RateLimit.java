package com.jyadmin.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 流量控制注解
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-07-07 00:13 <br>
 * @description: Limit <br>
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {

    /**
     * 缓存key
     */
    String key() default "";

    /**
     * 缓存前缀
     * 默认使用 JyLimitProperties配置
     */
    String prefix() default "";

    /**
     * 默认限制间隔时间，单位秒
     * 默认使用 JyLimitProperties配置
     */
    int period() default 0;

    /**
     * 默认限制访问次数
     * 默认使用 JyLimitProperties配置
     */
    int count() default 0;

    /**
     * 限制类型
     * 默认使用 JyLimitProperties配置
     */
    String limitType() default "";

}
