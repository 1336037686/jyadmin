package com.jyadmin.module.category.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jyadmin.domain.base.BaseEntity;
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