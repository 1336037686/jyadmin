package com.jyblog.module.category.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jyblog.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 博客类别表
 * @TableName tb_category
 */
@TableName(value ="tb_category")
@Data
public class Category extends BaseEntity implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 类别名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 类别编码
     */
    @TableField(value = "code")
    private String code;

    /**
     * 简介
     */
    @TableField(value = "intro")
    private String intro;

}