package com.jyblog.system.role.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jyblog.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统角色表
 * @TableName sys_role
 */
@TableName(value ="sys_role")
@Data
public class Role extends BaseEntity implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id")
    private String id;

    /**
     * 角色名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 角色编码
     */
    @TableField(value = "code")
    private String code;

    /**
     * 角色描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 角色排序
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 状态
     */
    @TableField(value = "status")
    private Integer status;

}