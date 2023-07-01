package com.jyadmin.system.email.record.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jyadmin.domain.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 邮件发送记录
 * @TableName sys_email_record
 */
@TableName(value ="sys_email_record")
@Data
public class EmailRecord extends BaseEntity implements Serializable {

    /**
     * 发送人
     */
    @TableField(value = "sender")
    private String sender;

    /**
     * 接收人
     */
    @TableField(value = "receiver")
    private String receiver;

    /**
     * 主题
     */
    @TableField(value = "subject")
    private String subject;

    /**
     * 邮件内容
     */
    @TableField(value = "body")
    private String body;

    /**
     * 邮件平台
     */
    @TableField(value = "source")
    private String source;

    /**
     * 业务标识
     */
    @TableField(value = "relevance")
    private String relevance;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}