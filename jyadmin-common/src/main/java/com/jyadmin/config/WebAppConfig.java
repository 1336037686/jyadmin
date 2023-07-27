package com.jyadmin.config;

import com.jyadmin.config.properties.JyBaseProperties;
import com.jyadmin.config.properties.JySecurityProperties;
import com.jyadmin.interceptor.DemoSystemHttpMethodInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 常规WebApp配置类
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-04 22:32 <br>
 * @description: JyWebAppConfig <br>
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Resource
    private JySecurityProperties securityProperties;

    @Resource
    private JyBaseProperties baseProperties;

    @Resource
    private DemoSystemHttpMethodInterceptor demoSystemHttpMethodInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 演示系统拦截器
        if (Boolean.TRUE.equals(baseProperties.getEnableDemoIntercept())) addDemoSystemHttpMethodInterceptor(registry);
    }

    /**
     * 演示系统拦截器
     * @param registry /
     * @return InterceptorRegistry
     */
    public void addDemoSystemHttpMethodInterceptor(InterceptorRegistry registry) {
        // 将演示系统拦截器添加到InterceptorRegistry中，并指定拦截的路径
        registry.addInterceptor(demoSystemHttpMethodInterceptor)
                // 拦截所有路径
                .addPathPatterns("/**")
                // 排除公共的不拦截的路径
                .excludePathPatterns(securityProperties.getIgnoreUrls())
                // 演示系统退出登录不拦截
                .excludePathPatterns("/api/auth/logout");
    }

}
