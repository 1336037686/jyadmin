package com.jyadmin.system.role.domain;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jyadmin.domain.base.BaseEntity;
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
     * 角色接口权限（api_permission_portion=根据角色菜单限制接口权限， api_permission_all=拥有全部接口权限）
     */
    @TableField(value = "api_permission")
    private String apiPermission;

    /**
     * 数据范围（all=全部，local=本级，other=自定义）
     */
    @TableField(value = "data_scope")
    private String dataScope;

    /**
     * 自定义数据范围（ID集合，数据之间用,分隔）
     */
    @TableField(value = "user_define_data_scope", updateStrategy = FieldStrategy.IGNORED)
    private String userDefineDataScope;

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