package com.jyblog.system.permission.menu.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * 系统权限菜单表
 * @TableName sys_permission_menu
 */
@TableName(value ="sys_permission_menu")
@Data
public class PermissionMenu implements Serializable {


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id")
    private String id;

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
    private String parent_id;

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
    @TableField(value = "cache")
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