package com.jyadmin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口幂等注解
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-07-09 00:17 <br>
 * @description: Idempotent <br>
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {

    String name() default "";

}
