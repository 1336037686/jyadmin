package com.jyblog.module.blog.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * 博客文章表
 * @TableName tb_blog
 */
@TableName(value ="tb_blog")
@Data
public class Blog implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

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