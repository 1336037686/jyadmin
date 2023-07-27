package com.jyadmin.interceptor;

import com.jyadmin.consts.ResultStatus;
import com.jyadmin.domain.Result;
import com.jyadmin.util.ResponseUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 演示系统请求拦截器
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-07-27 10:07 <br>
 * @description: DemoSystemHttpMethodInterceptor <br>
 */
@Component
public class DemoSystemHttpMethodInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在这里编写拦截逻辑
        // 获取请求方法
        String method = request.getMethod();

        // 如果是POST、PUT或DELETE请求，执行拦截逻辑
        if ("POST".equals(method) || "PUT".equals(method) || "DELETE".equals(method)) {
            // 返回一个错误响应，表示不允许该操作
            ResponseUtil.out(response, Result.fail(ResultStatus.DEMO_SYSTEM_NOT_ALLOW_OPERATION));
            return false; // 返回false表示拦截请求
        }
        // 返回true表示不拦截请求，继续执行后续操作
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 这个方法在Controller方法调用之后，视图渲染之前调用，可以添加一些处理逻辑
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 这个方法在整个请求完成之后调用，可以添加一些清理资源的逻辑
    }

}
