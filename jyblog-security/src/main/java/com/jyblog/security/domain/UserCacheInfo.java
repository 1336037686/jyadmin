package com.jyblog.security.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-05-10 23:18 <br>
 * @description: UserCacheInfo <br>
 */
@Data
@Accessors(chain = true)
public class UserCacheInfo implements Serializable {

    /**
     * 登录用户
     */
    private String username;

    /**
     * 用户权限
     */
    private List<String> permissions;

    /**
     * ip 地址
     */
    private String ipAddress;

    /**
     * 所属地区
     */
    private String ipArea;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 登陆时间
     */
    private LocalDateTime createTime;

}
