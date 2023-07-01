package com.jyadmin.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
     * ID
     */
    @ApiModelProperty(value = "id", name = "id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 登录用户
     */
    @ApiModelProperty(value = "登录用户", name = "username")
    private String username;

    /**
     * 密码
     */
    @JsonIgnore
    @ApiModelProperty(value = "密码", name = "password")
    private String password;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称", name = "nickname")
    private String nickname;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像", name = "avatar")
    private String avatar;

    /**
     * 电话号码
     */
    @ApiModelProperty(value = "电话号码", name = "phone")
    private String phone;

    /**
     * 账号类型
     */
    @ApiModelProperty(value = "账号类型", name = "type")
    private Integer type;

    /**
     * 部门
     */
    @ApiModelProperty(value = "部门", name = "department")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long department;

    /**
     * 岗位
     */
    @ApiModelProperty(value = "岗位", name = "post")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long post;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", name = "status")
    private Integer status;


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

    /**
     * 当前用户权限
     */
    @ApiModelProperty(value = "当前用户权限", name = "permissions")
    private List<String> permissions;

}
