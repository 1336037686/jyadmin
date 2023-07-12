package com.jyadmin.system.announcements.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jyadmin.domain.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * 系统公告 <br>
 * @TableName sys_announcements <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-07-12 20:08:30 <br>
 * @description: Announcements <br>
 */
@TableName(value ="sys_announcements")
@Accessors(chain = true)
@Data
public class Announcements extends BaseEntity implements Serializable {

    /**
     * 公告标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 公告内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 公告状态草稿（draft）、已发布（published）、已过期（expired）
     */
    @TableField(value = "status")
    private String status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}