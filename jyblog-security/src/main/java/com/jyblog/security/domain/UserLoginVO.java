package com.jyblog.security.domain;

import lombok.Data;

/**
 * @author LGX_TvT
 * @date 2022-04-29 10:46
 */
@Data
public class UserLoginVO {

    /**
     * 帐号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

}
