package com.jyadmin.system.permission.menu.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jyadmin.domain.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统权限菜单表
 * @TableName sys_permission_menu
 */
@TableName(value ="sys_permission_menu")
@Data
public class PermissionMenu extends BaseEntity implements Serializable {


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 菜单名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 菜单标识
     */
    @TableField(value = "code")
    private String code;

    /**
     * 菜单类别
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 父类ID
     */
    @TableField(value = "parent_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;

    /**
     * 路由地址
     */
    @TableField(value = "url")
    private String url;

    /**
     * 组件路径
     */
    @TableField(value = "path")
    private String path;

    /**
     * 组件名称
     */
    @TableField(value = "component")
    private String component;

    /**
     * 调用方法
     */
    @TableField(value = "method")
    private String method;

    /**
     * 图标
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 样式
     */
    @TableField(value = "style")
    private String style;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 是否缓存
     */
    @TableField(value = "`cache`")
    private Integer cache;

    /**
     * 是否显示
     */
    @TableField(value = "visiable")
    private Integer visiable;

    /**
     * 是否外链
     */
    @TableField(value = "link")
    private Integer link;

    /**
     * 状态
     */
    @TableField(value = "status")
    private Integer status;

}