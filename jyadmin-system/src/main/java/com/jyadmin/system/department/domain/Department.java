package com.jyadmin.system.department.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jyadmin.domain.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 部门表
 * @TableName sys_department
 */
@TableName(value ="sys_department")
@Data
public class Department extends BaseEntity implements Serializable {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 部门名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 部门编号
     */
    @TableField(value = "code")
    private String code;

    /**
     * 是否顶级部门（0=非顶级部门，1=顶级部门）
     */
    @TableField(value = "is_root")
    private Integer isRoot;

    /**
     * 上级部门ID
     */
    @TableField(value = "parent_id")
    private String parentId;

    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 状态 0=禁用 1=启用
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}