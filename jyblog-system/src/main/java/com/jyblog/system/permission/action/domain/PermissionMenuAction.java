package com.jyblog.system.permission.action.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * 系统权限菜单动作中间表
 * @TableName tr_permission_menu_action
 */
@TableName(value ="tr_permission_menu_action")
@Data
public class PermissionMenuAction implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id")
    private String id;

    /**
     * 权限菜单ID
     */
    @TableField(value = "menu_id")
    private String menu_id;

    /**
     * 权限动作ID
     */
    @TableField(value = "action_id")
    private String action_id;

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