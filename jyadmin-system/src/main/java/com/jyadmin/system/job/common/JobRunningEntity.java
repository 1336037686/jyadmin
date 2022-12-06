package com.jyadmin.system.job.common;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-12-05 11:22 <br>
 * @description: JobRunningEntity <br>
 */
@Slf4j
public class JobRunningEntity {

    private Object bean;
    private Method method;
    private Map params;

    public JobRunningEntity(String beanName, String methodName, Map params) {
        try {
            this.bean = SpringUtil.getBean(beanName);
            this.params = params;
            Class beanClass = bean.getClass();
            if (CollectionUtil.isNotEmpty(params)) this.method = beanClass.getDeclaredMethod(methodName, Map.class);
            else this.method = beanClass.getDeclaredMethod(methodName);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            log.error("method获取异常 {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void run() {
        try {
            method.setAccessible(true);
            if (CollectionUtil.isNotEmpty(this.params)) method.invoke(this.bean, this.params);
            else method.invoke(bean);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("method执行异常 {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
