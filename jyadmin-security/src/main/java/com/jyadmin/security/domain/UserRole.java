package com.jyadmin.security.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jyadmin.domain.base.BaseTrEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 系统用户角色中间表
 * @TableName tr_user_role
 */
@TableName(value ="tr_user_role")
@Data
public class UserRole extends BaseTrEntity implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     * 角色ID
     */
    @TableField(value = "role_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long roleId;

}