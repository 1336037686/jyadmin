package com.jyadmin.module.blog.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jyadmin.domain.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 博客文章表
 * @TableName tb_blog
 */
@TableName(value ="tb_blog")
@Data
public class Blog extends BaseEntity implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


    /**
     * 文章名称
     */
    @TableField(value = "title")
    private String title;

    /**
     * 摘要
     */
    @TableField(value = "intro")
    private String intro;

    /**
     * 类别
     */
    @TableField(value = "category")
    private String category;

    /**
     * 标签
     */
    @TableField(value = "tag")
    private String tag;

    /**
     * 封面
     */
    @TableField(value = "cover")
    private String cover;

    /**
     * 文章来源
     */
    @TableField(value = "source")
    private Integer source;

    /**
     * 文章内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 状态
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 仅自己可见
     */
    @TableField(value = "visible")
    private Integer visible;

    /**
     * 作者
     */
    @TableField(value = "author")
    private String author;

}