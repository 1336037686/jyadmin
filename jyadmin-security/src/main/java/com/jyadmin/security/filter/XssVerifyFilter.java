package com.jyadmin.security.filter;

import com.jyadmin.config.properties.JyXssFilterProperties;
import com.jyadmin.consts.ResultStatus;
import com.jyadmin.domain.Result;
import com.jyadmin.security.model.JsonBodyHttpServletRequestWrapper;
import com.jyadmin.util.RequestUtil;
import com.jyadmin.util.ResponseUtil;
import com.jyadmin.util.XssUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Xss校验
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-05-29 22:34 <br>
 * @description: XssVerifyFilter <br>
 */
@Slf4j
public class XssVerifyFilter extends OncePerRequestFilter {

    @Resource
    private JyXssFilterProperties jyXssFilterProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 判断当前请求是不是application/json请求, 如果不是，直接放行
        String contentType = request.getContentType();
        if (StringUtils.isBlank(contentType) || !contentType.contains("application/json")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 配置是否放行全部接口
        if (Boolean.TRUE.equals(jyXssFilterProperties.getPermitAll())) {
            // 继续执行下一个过滤器
            filterChain.doFilter(request, response);
            return;
        }

        // 判断是否是白名单路径，如果为白名单路径，直接放行
        if (RequestUtil.isIgnoreUrl(request, jyXssFilterProperties.getIgnoreUrls())) {
            // 继续执行下一个过滤器
            filterChain.doFilter(request, response);
            return;
        }

        // 进行参数校验
        JsonBodyHttpServletRequestWrapper requestWrapper = new JsonBodyHttpServletRequestWrapper(request);
        // 校验参数， 包含敏感信息则直接返回错误信息
        String requestBody = requestWrapper.getBody();
        if (XssUtil.containsSensitiveWords(requestBody)) {
            ResponseUtil.out(response, Result.fail(ResultStatus.PARAM_XSS_SENSITIVITY_INFO));
            return;
        }

        // 处理请求体数据
        filterChain.doFilter(requestWrapper, response);
    }
}
