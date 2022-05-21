package com.jyadmin.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-05-10 23:18 <br>
 * @description: UserCacheInfo <br>
 */
@ApiModel("登录用户缓存")
@Data
@Accessors(chain = true)
public class UserCacheInfo implements Serializable {

    /**
     * 登录用户
     */
    @ApiModelProperty(value = "登录用户", name = "username")
    private String username;

    /**
     * 用户权限
     */
    @JsonIgnore
    @ApiModelProperty(value = "用户权限", name = "permissions")
    private List<String> permissions;

    /**
     * ip 地址
     */
    @ApiModelProperty(value = "ip地址", name = "ipAddress")
    private String ipAddress;

    /**
     * 所属地区
     */
    @ApiModelProperty(value = "所属地区", name = "ipArea")
    private String ipArea;

    /**
     * 浏览器
     */
    @ApiModelProperty(value = "浏览器", name = "browser")
    private String browser;

    /**
     * 登陆时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "登陆时间", name = "createTime")
    private Date createTime;

}
