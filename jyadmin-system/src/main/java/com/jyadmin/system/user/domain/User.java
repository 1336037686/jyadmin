package com.jyadmin.system.user.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jyadmin.domain.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户表
 * @TableName sys_user
 */
@TableName(value ="sys_user")
@Data
public class User extends BaseEntity implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 帐号
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @JsonIgnore
    @TableField(value = "password")
    private String password;

    /**
     * 昵称
     */
    @TableField(value = "nickname")
    private String nickname;

    /**
     * 头像
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 电话号码
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 账号类型
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 所属部门
     */
    @TableField(value = "department")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long department;

    /**
     * 所属岗位
     */
    @TableField(value = "post")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long post;

    /**
     * 状态
     */
    @TableField(value = "status")
    private Integer status;

}