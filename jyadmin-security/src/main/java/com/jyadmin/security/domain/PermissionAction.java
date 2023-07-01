package com.jyadmin.security.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jyadmin.domain.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统权限动作表
 * @TableName sys_permission_action
 */
@TableName(value ="sys_permission_action")
@Data
public class PermissionAction extends BaseEntity implements Serializable {


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

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
    @JsonSerialize(using = ToStringSerializer.class)
    private Long groupId;

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

}