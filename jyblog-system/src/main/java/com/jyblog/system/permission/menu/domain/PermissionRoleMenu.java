package com.jyblog.system.permission.menu.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * 系统角色权限中间表
 * @TableName tr_permission_role_menu
 */
@TableName(value ="tr_permission_role_menu")
@Data
public class PermissionRoleMenu implements Serializable {

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
    private String role_id;

    /**
     * 权限菜单ID
     */
    @TableField(value = "menu_id")
    private String menu_id;

    /**
     * 创建人
     */
    @JsonIgnore
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @JsonIgnore
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}