package com.jyadmin.security.handler;

import com.jyadmin.consts.ResultStatus;
import com.jyadmin.domain.Result;
import com.jyadmin.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 未授权处理类
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-12 21:34 <br>
 * @description: DefaultUnAuthHandler <br>
 */
@Slf4j
@Component("defaultUnAuthHandler")
public class DefaultUnAuthHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("DefaultUnAuthHandler 认证未通过，不允许访问异常, {}", authException.getMessage());
        // 请求未授权
        ResponseUtil.out(response, Result.fail(ResultStatus.REQUEST_NOT_AUTHORIZED));
    }
}
