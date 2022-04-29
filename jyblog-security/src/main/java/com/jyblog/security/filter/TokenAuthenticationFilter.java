package com.jyblog.security.filter;

import com.jyblog.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("TokenFilter");
        String token = request.getHeader("x-token");
        // 判断token是否存在 且 Token合法
        if (StringUtils.isNotBlank(token) && JWTUtil.verify(token)) {
            String username = JWTUtil.parseToken(token);
            // SecurityContextHolder.getContext().getAuthentication() == null 未认证则为true
            if (StringUtils.isNotBlank(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (userDetails != null) {
                    // 将用户信息存入 authentication，方便后续校验
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // 将authentication存入ThreadLocal，方便后续获取用户信息
                    SecurityContextHolder. getContext ().setAuthentication(authentication);
                }
            }
        }
        // 继续执行下一个过滤器
        filterChain.doFilter(request, response);
    }
}
