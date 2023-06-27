package com.jyadmin.system.role.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@Accessors(chain = true)
public class UserRole extends BaseTrEntity implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 角色ID
     */
    @TableField(value = "role_id")
    private String roleId;

}