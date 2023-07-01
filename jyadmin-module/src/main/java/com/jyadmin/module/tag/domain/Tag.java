package com.jyadmin.module.tag.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jyadmin.domain.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 博客标签表
 * @TableName tb_tag
 */
@TableName(value ="tb_tag")
@Data
public class Tag extends BaseEntity implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 标签名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 标签编码
     */
    @TableField(value = "code")
    private String code;

    /**
     * 简介
     */
    @TableField(value = "intro")
    private String intro;

}