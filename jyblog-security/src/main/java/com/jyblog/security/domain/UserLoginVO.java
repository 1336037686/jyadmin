package com.jyblog.security.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author LGX_TvT
 * @date 2022-04-29 10:46
 */
@Data
@Accessors(chain = true)
public class UserLoginVO implements Serializable {

    /**
     * 帐号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

}
