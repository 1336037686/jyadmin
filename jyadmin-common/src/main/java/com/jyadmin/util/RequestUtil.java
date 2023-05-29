package com.jyadmin.util;

import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * Request工具类
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-05-29 22:36 <br>
 * @description: RequestUtil <br>
 */
public class RequestUtil {

    /**
     * 当前请求是否是白名单路径
     * @param request 请求
     * @param ignoreUrls 白名单路径数组
     * @return boolean
     */
    public static boolean isIgnoreUrl(HttpServletRequest request, String[] ignoreUrls) {
        String path = request.getServletPath();
        AntPathMatcher matcher = new AntPathMatcher();
        for (String ignoreUrl : ignoreUrls) {
            boolean match = matcher.match(ignoreUrl, path);
            if (match) return match;
        }
        return false;
    }

}
