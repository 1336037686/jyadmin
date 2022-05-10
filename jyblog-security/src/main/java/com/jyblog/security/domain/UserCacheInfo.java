package com.jyblog.security.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
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

    //当前登录用户
    private User currentUser;

    //当前权限
    private List<String> permissions;

}
