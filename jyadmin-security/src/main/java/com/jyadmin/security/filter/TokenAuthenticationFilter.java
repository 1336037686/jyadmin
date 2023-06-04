package com.jyadmin.security.filter;

import cn.hutool.core.bean.BeanUtil;
import com.google.common.collect.Lists;
import com.jyadmin.config.properties.JySecurityProperties;
import com.jyadmin.consts.GlobalConstants;
import com.jyadmin.consts.ResultStatus;
import com.jyadmin.domain.Result;
import com.jyadmin.domain.UserCacheInfo;
import com.jyadmin.security.domain.SecurityUser;
import com.jyadmin.security.domain.User;
import com.jyadmin.security.service.CacheService;
import com.jyadmin.util.IpUtil;
import com.jyadmin.util.JWTUtil;
import com.jyadmin.util.RequestUtil;
import com.jyadmin.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
        // 配置是否放行全部接口
        if (Boolean.TRUE.equals(jySecurityProperties.getPermitAll())) {
            // 继续执行下一个过滤器
            filterChain.doFilter(request, response);
            return;
        }

        // 判断是否是白名单路径，如果为白名单路径，直接放行
        if (RequestUtil.isIgnoreUrl(request, jySecurityProperties.getIgnoreUrls())) {
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

        // 获取登录缓存信息
        UserCacheInfo userCacheInfo = cacheService.get(username);

        // 根据IP判断当前账号是否别处登录，如果异地登录返回错误信息
        if (Boolean.TRUE.equals(jySecurityProperties.getSingleIpLogin())) {
            String ip = IpUtil.getIp(request);
            if (!ip.equals(userCacheInfo.getIpAddress())) {
                ResponseUtil.out(response, Result.fail(ResultStatus.REMOTE_LOGIN_ERROR));
                return;
            }
        }

        // 当前状态为已认证状态，执行下一个过滤器
        if (Objects.nonNull(SecurityContextHolder.getContext().getAuthentication())) {
            filterChain.doFilter(request, response);
            return;
        }

        // 如果当前用户登录信息在缓存中存在，且当前状态为未认证状态，则添加登录认证信息。
        // SecurityContextHolder.getContext().getAuthentication() == null 未认证则为true

        // 获取缓存中登录用户信息，缓存中不存在当前登录用户，返回错误信息
         UserDetails userDetails = this.buildUserDetails(userCacheInfo);
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

    /**
     * 通过缓存的登录用户信息 构建 UserDetails
     * @param userCacheInfo 缓存的登录用户信息
     * @return UserDetails
     */
    public UserDetails buildUserDetails(UserCacheInfo userCacheInfo) {
        if (Objects.isNull(userCacheInfo.getId())) return null;
        User user = new User();
        BeanUtil.copyProperties(userCacheInfo, user);
        List<String> permissions = userCacheInfo.getPermissions();
        if (CollectionUtils.isEmpty(permissions)) permissions = Lists.newArrayList();
        return new SecurityUser().setCurrentUser(user).setPermissions(permissions);
    }


}
