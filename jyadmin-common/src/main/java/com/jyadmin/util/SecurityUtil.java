package com.jyadmin.util;

import cn.hutool.json.JSONObject;
import com.jyadmin.consts.ResultStatus;
import com.jyadmin.exception.ApiException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 登录信息工具类
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-16 17:18 <br>
 * @description: SecurityUtils <br>
 */
public class SecurityUtil {

    /**
     * 登录用户 SecurityUser -> 当前登录用户 字段名：currentUser
     * com.jyadmin.security.domain.SecurityUser
     */
    private static final String SECURITY_LOGIN_USER_INFO_KEY = "currentUser";

    /**
     * 当前登录用户 User ID，字段名：ID
     * com.jyadmin.security.domain.User
     */
    private static final String SECURITY_LOGIN_USER_ID_KEY = "id";

    /**
     * 获取当前登录的用户
     * @return UserDetails
     */
    public static UserDetails getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 当前登录状态过期
        if (authentication == null) throw new ApiException(ResultStatus.LOGIN_STATUS_EXPIRED);
        // 登录信息存在直接返回
        if (authentication.getPrincipal() instanceof UserDetails) return (UserDetails) authentication.getPrincipal();
        // 找不到当前登录的信息
        throw new ApiException(ResultStatus.NOT_FOUND_LOGIN_INFO);
    }

    /**
     * 获取系统用户名称
     *
     * @return 系统用户名称
     */
    public static String getCurrentUsername() {
        return getCurrentUser().getUsername();
    }

    /**
     * 获取系统用户ID
     * @return 系统用户ID
     */
    public static Long getCurrentUserId() {
        UserDetails userDetails = getCurrentUser();
        // 获取登录信息内部用户实体类
        JSONObject userDetailsJSON = new JSONObject(userDetails.toString());
        if (!userDetailsJSON.containsKey(SECURITY_LOGIN_USER_INFO_KEY)) throw new ApiException(ResultStatus.LOGIN_INFO_OBTAIN_ERROR);
        final JSONObject currentUser = new JSONObject(userDetailsJSON.get(SECURITY_LOGIN_USER_INFO_KEY));
        // 获取登录用户ID
        if (!currentUser.containsKey(SECURITY_LOGIN_USER_ID_KEY)) throw new ApiException(ResultStatus.LOGIN_INFO_OBTAIN_ERROR);
        return currentUser.get(SECURITY_LOGIN_USER_ID_KEY, Long.class);
    }

}
