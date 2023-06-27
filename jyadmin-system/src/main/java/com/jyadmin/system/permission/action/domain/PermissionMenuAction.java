package com.jyadmin.system.permission.action.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 权限菜单ID
     */
    @TableField(value = "menu_id")
    private String menuId;

    /**
     * 权限动作ID
     */
    @TableField(value = "action_id")
    private String actionId;

}