package com.jyadmin.security.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

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
     * 部门
     */
    @TableField(value = "department")
    private String department;

    /**
     * 岗位
     */
    @TableField(value = "post")
    private String post;

    /**
     * 状态
     */
    @TableField(value = "status")
    private Integer status;

}