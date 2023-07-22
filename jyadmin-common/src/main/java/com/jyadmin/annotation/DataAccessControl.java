package com.jyadmin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据访问控制注解
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-06-06 17:40 <br>
 * @description: DataAccessControl <br>
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataAccessControl {

    /**
     * 进行数据访问控制的方法，该参数只标识在Class上启用
     * com.jyadmin.consts.DataAccessControlConstant#DEFAULT_BASE_MAPPER_SELECT_METHODS
     */
    String[] methods() default { };

    /**
     * 是否对MybatisPlus mapper 默认的方法启用数据权限控制
     * com.jyadmin.consts.DataAccessControlConstant#DEFAULT_BASE_MAPPER_SELECT_METHODS
     */
    boolean enableMybatisPlusDefaultMethods() default false;

}
