package com.jyblog.system.permission.menu.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jyblog.domain.BaseTrEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 系统角色权限中间表
 * @TableName tr_permission_role_menu
 */
@TableName(value ="tr_permission_role_menu")
@Data
@Accessors(chain = true)
public class PermissionRoleMenu extends BaseTrEntity implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id")
    private String id;

    /**
     * 角色ID
     */
    @TableField(value = "role_id")
    private String roleId;

    /**
     * 权限菜单ID
     */
    @TableField(value = "menu_id")
    private String menuId;

}