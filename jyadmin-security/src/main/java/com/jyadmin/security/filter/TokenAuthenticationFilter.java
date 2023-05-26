package com.jyadmin.security.filter;

import com.jyadmin.config.properties.JySecurityProperties;
import com.jyadmin.consts.GlobalConstants;
import com.jyadmin.consts.ResultStatus;
import com.jyadmin.domain.Result;
import com.jyadmin.exception.ApiException;
import com.jyadmin.security.service.CacheService;
import com.jyadmin.util.JWTUtil;
import com.jyadmin.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * Token校验过滤器
 *
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-12 23:03 <br>
 * @description: TokenFilter <br>
 */
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private JySecurityProperties jySecurityProperties;

    @Resource
    private CacheService cacheService;

    @Resource
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 配置放行是否全部接口
        if (jySecurityProperties.getPermitAll()) {
            // 继续执行下一个过滤器
            filterChain.doFilter(request, response);
            return;
        }

        // 判断是否是白名单路径，如果为白名单路径，直接放行
        if (isIgnoreUrl(request)) {
            // 继续执行下一个过滤器
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader(GlobalConstants.SYS_LOGIN_TOKEN_PARAM_NAME);
        // 如果当前token不存在，返回错误信息
        if (StringUtils.isBlank(token)) {
            ResponseUtil.out(response, Result.fail(ResultStatus.TOKEN_NOT_EXIST));
            return;
        }

        // Token过期，返回错误信息
        if (!JWTUtil.validateTime(token)) {
            ResponseUtil.out(response, Result.fail(ResultStatus.TOKEN_EXPIR_ERROR));
            return;
        }

        // Token不合法，返回错误信息
        if (!JWTUtil.validateLegal(token)) {
            ResponseUtil.out(response, Result.fail(ResultStatus.TOKEN_FORMAT_ERROR));
            return;
        }

        // 如果当前token解析的username不存在，返回错误信息
        String username = JWTUtil.parseToken(token);
        if (StringUtils.isBlank(username)) {
            ResponseUtil.out(response, Result.fail(ResultStatus.TOKEN_FORMAT_ERROR));
            return;
        }

        // 缓存中不存在当前登录用户，返回错误信息
        if (!cacheService.isExist(username)) {
            ResponseUtil.out(response, Result.fail(ResultStatus.LOGIN_STATUS_EXPIRED));
            return;
        }

        // 当前状态为已认证状态，执行下一个过滤器
        if (Objects.nonNull(SecurityContextHolder.getContext().getAuthentication())) {
            filterChain.doFilter(request, response);
            return;
        }

        // 如果当前用户登录信息在缓存中存在，且当前状态为未认证状态，则添加登录认证信息。
        // SecurityContextHolder.getContext().getAuthentication() == null 未认证则为true
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // 如果查询不到登录用户信息，返回错误信息
        if (Objects.isNull(userDetails)) {
            ResponseUtil.out(response, Result.fail(ResultStatus.NOT_FOUND_LOGIN_INFO));
            return;
        }

        // 添加登录认证信息，将用户信息存入 authentication，方便后续校验
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // 将authentication存入ThreadLocal，方便后续获取用户信息
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 继续执行下一个过滤器
        filterChain.doFilter(request, response);
    }


    private boolean isIgnoreUrl(HttpServletRequest request) {
        String path = request.getServletPath();
        AntPathMatcher matcher = new AntPathMatcher();
        String[] ignoreUrls = jySecurityProperties.getIgnoreUrls();
        for (String ignoreUrl : ignoreUrls) {
            boolean match = matcher.match(ignoreUrl, path);
            if (match) return match;
        }
        return false;
    }

}
