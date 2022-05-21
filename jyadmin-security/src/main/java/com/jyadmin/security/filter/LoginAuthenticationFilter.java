package com.jyadmin.security.filter;

import com.jyadmin.consts.JyResultStatus;
import com.jyadmin.domain.Result;
import com.jyadmin.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户登录认证过滤器
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-11 00:07 <br>
 * @description: LoginFilter <br>
 */
@Slf4j
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * 登录失败处理
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("登录失败");

        // 账号被锁定
        if (failed instanceof LockedException) {
            ResponseUtil.out(response, Result.fail(JyResultStatus.ACCOUNT_LOCKOUT));
            return;
        }

        // 密码过期
        if (failed instanceof CredentialsExpiredException) {
            ResponseUtil.out(response, Result.fail(JyResultStatus.PASSWORD_EXPIRATION));
            return;
        }

        // 账户过期
        if (failed instanceof AccountExpiredException) {
            ResponseUtil.out(response, Result.fail(JyResultStatus.ACCOUNT_EXPIRATION));
            return;
        }

        // 账户被禁用
        if (failed instanceof DisabledException) {
            ResponseUtil.out(response, Result.fail(JyResultStatus.ACCOUNT_DISABLED));
            return;
        }

        // 用户名或者密码输入错误
        if (failed instanceof BadCredentialsException) {
            ResponseUtil.out(response, Result.fail(JyResultStatus.USERNAME_PASSWORD_ERROR));
        }
    }
}
