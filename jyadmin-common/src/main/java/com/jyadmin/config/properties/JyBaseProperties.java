package com.jyadmin.config.properties;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 基础配置
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-06-27 09:55 <br>
 * @description: JyBaseProperties <br>
 */
@ConfigurationProperties(prefix = JyConfigItemConstant.BASE)
@Component
@Data
public class JyBaseProperties {

    /**
     * 超级管理员 账户名
     * 该账户将会拥有所有权限，全部放行
     */
    private List<String> superAdmins = Lists.newArrayList("admin");

    /**
     * 演示系统拦截功能
     */
    private Boolean enableDemoIntercept = false;


}
