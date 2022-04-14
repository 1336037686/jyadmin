package com.jyblog.system.permission.group.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统权限动作组别表
 * @TableName sys_permission_action_group
 */
@TableName(value ="sys_permission_action_group")
@Data
public class PermissionActionGroup implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id")
    private String id;

    /**
     * 权限组别名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 权限组别标识
     */
    @TableField(value = "code")
    private String code;

    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 父类ID
     */
    @TableField(value = "parent_id")
    private String parentId;

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