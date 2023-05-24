package com.jyadmin.system.post.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jyadmin.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 岗位表
 * @TableName sys_post
 */
@TableName(value ="sys_post")
@Data
public class Post extends BaseEntity implements Serializable {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 岗位名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 岗位编号
     */
    @TableField(value = "code")
    private String code;

    /**
     * 岗位简介
     */
    @TableField(value = "description")
    private String description;

    /**
     * 状态 0=禁用 1=启用
     */
    @TableField(value = "status")
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}