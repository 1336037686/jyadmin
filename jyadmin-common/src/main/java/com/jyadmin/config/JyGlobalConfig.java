package com.jyadmin.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;

/**
 * 全局配置 统一管理
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-07-04 22:25 <br>
 * @description: JyGlobalConfig <br>
 */
@EnableConfigurationProperties
@Import(BeanValidatorPluginsConfiguration.class) // 开启使用JSR303注解 https://doc.xiaominfo.com/knife4j/documentation/jsr303.html
@Configuration
public class JyGlobalConfig {
}
