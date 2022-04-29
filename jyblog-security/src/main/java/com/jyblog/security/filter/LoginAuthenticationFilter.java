package com.jyblog.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jyblog.domain.Result;
import com.jyblog.security.domain.UserLoginVO;
import com.jyblog.util.JWTUtil;
import com.jyblog.util.ResponseUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户登录认证过滤器
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-11 00:07 <br>
 * @description: LoginFilter <br>
 */
@Slf4j
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public LoginAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher( "/login", "POST"));
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UserLoginVO user = new ObjectMapper().readValue(request.getInputStream(), UserLoginVO.class);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        return getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * 登录成功处理
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authResult);
        // 返回两个token
        String accessToken = JWTUtil.createAccessToken(userDetails.getUsername());
        String refreshToken = JWTUtil.createRefreshToken(userDetails.getUsername());

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("accessToken", accessToken);
        tokenMap.put("refreshToken", refreshToken);
        ResponseUtil.out(response, Result.ok(200, "登陆成功", tokenMap));
    }

    /**
     * 登录失败处理
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("登录失败");

        if (failed instanceof LockedException) {
            ResponseUtil.out(response, Result.fail(40001, "账号被锁定，请联系管理员"));
            return;
        }

        if (failed instanceof CredentialsExpiredException) {
            ResponseUtil.out(response, Result.fail(40002, "密码过期，请联系管理员"));
            return;
        }

        if (failed instanceof AccountExpiredException) {
            ResponseUtil.out(response, Result.fail(40003, "账户过期，请联系管理员"));
            return;
        }

        if (failed instanceof DisabledException) {
            ResponseUtil.out(response, Result.fail(40004, "账户被禁用，请联系管理员"));
            return;
        }

        if (failed instanceof BadCredentialsException) {
            ResponseUtil.out(response, Result.fail(40005, "用户名或者密码输入错误，请重新输入"));
        }

    }
}
