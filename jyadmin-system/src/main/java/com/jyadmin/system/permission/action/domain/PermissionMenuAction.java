package com.jyadmin.system.permission.action.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jyadmin.domain.base.BaseTrEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 系统权限菜单动作中间表
 * @TableName tr_permission_menu_action
 */
@TableName(value ="tr_permission_menu_action")
@Data
@Accessors(chain = true)
public class PermissionMenuAction extends BaseTrEntity implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 权限菜单ID
     */
    @TableField(value = "menu_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long menuId;

    /**
     * 权限动作ID
     */
    @TableField(value = "action_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long actionId;

}