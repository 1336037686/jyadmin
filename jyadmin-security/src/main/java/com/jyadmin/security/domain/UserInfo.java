package com.jyadmin.security.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 登录用户信息
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-06-29 23:40 <br>
 * @description: UserInfo <br>
 */
@Data
public class UserInfo extends User implements Serializable {

    /**
     * 角色
     */
    private List<String> roles;

    /**
     * 权限
     */
    private List<String> permissions;

}
