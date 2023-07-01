package com.jyadmin.util;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.jyadmin.consts.ResultStatus;
import com.jyadmin.exception.ApiException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 登录信息工具类
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-16 17:18 <br>
 * @description: SecurityUtils <br>
 */
public class SecurityUtil {

    /**
     * 获取当前登录的用户
     * @return UserDetails
     */
    public static UserDetails getCurrentUser() {
        UserDetailsService userDetailsService = SpringUtil.getBean(UserDetailsService.class);
        return userDetailsService.loadUserByUsername(getCurrentUsername());
    }

    /**
     * 获取系统用户名称
     *
     * @return 系统用户名称
     */
    public static String getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            // 当前登录状态过期
            throw new ApiException(ResultStatus.LOGIN_STATUS_EXPIRED);
        }
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        // 找不到当前登录的信息
        throw new ApiException(ResultStatus.NOT_FOUND_LOGIN_INFO);
    }

    /**
     * 获取系统用户ID
     * @return 系统用户ID
     */
    public static Long getCurrentUserId() {
        UserDetails userDetails = getCurrentUser();
        final JSONObject currentUser = new JSONObject(new JSONObject(userDetails.toString()).get("currentUser"));
        return currentUser.get("id", Long.class);
    }

}
