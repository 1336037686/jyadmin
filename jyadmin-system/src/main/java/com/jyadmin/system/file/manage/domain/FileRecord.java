package com.jyadmin.system.file.manage.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jyadmin.domain.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 附件文件记录
 * @TableName sys_file_record
 */
@TableName(value ="sys_file_record")
@Data
@Accessors(chain = true)
public class FileRecord extends BaseEntity implements Serializable {

    /**
     * 文件名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 文件类型
     */
    @TableField(value = "type")
    private String type;

    /**
     * 文件大小
     */
    @TableField(value = "size")
    private Long size;

    /**
     * 文件后缀
     */
    @TableField(value = "suffix")
    private String suffix;

    /**
     * 文件路径
     */
    @TableField(value = "path")
    private String path;

    /**
     * 文件相对路径
     */
    @TableField(value = "relative_path")
    private String relativePath;

    /**
     * 文件存储平台（阿里云、七牛云、腾讯云、本地）
     */
    @TableField(value = "source")
    private String source;

    /**
     * 原文件名称
     */
    @TableField(value = "real_name")
    private String realName;

    /**
     * 业务标识
     */
    @TableField(value = "relevance")
    private String relevance;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}