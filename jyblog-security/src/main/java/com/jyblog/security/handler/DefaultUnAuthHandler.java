package com.jyblog.security.handler;

import com.jyblog.domain.Result;
import com.jyblog.util.ResponseUtil;
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
        ResponseUtil.out(response, Result.fail(400, "请求未授权"));
    }
}
