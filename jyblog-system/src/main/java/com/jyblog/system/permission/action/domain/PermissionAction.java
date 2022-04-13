package com.jyblog.system.permission.action.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * 系统权限动作表
 * @TableName sys_permission_action
 */
@TableName(value ="sys_permission_action")
@Data
public class PermissionAction implements Serializable {


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id")
    private String id;

    /**
     * 动作名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 权限标识
     */
    @TableField(value = "code")
    private String code;

    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 所属权限组
     */
    @TableField(value = "group_id")
    private String group_id;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 状态
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 创建人
     */
    @JsonIgnore
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 更新人
     */
    @JsonIgnore
    @TableField(value = "update_by")
    private String updateBy;

    /**
     * 创建时间
     */
    @JsonIgnore
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonIgnore
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    @JsonIgnore
    @TableField(value = "deleted")
    @TableLogic
    private Integer deleted;

}