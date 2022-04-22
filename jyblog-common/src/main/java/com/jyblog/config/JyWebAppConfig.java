package com.jyblog.config;

import com.jyblog.util.SpringContextHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-04 22:32 <br>
 * @description: JyWebAppConfig <br>
 */
@Configuration
public class JyWebAppConfig implements WebMvcConfigurer {


    @Resource
    private ApplicationContext applicationContext;


    /**
     * 注册SpringContextHolder
     * @return
     */
    @Bean
    public SpringContextHolder registerSpringContextHolder() {
        SpringContextHolder springContextHolder = new SpringContextHolder();
        springContextHolder.setApplicationContext(applicationContext);
        return springContextHolder;
    }


}
