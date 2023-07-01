package com.jyadmin.system.sms.record.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jyadmin.domain.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 短信发送记录
 * @TableName sys_sms_record
 */
@TableName(value ="sys_sms_record")
@Data
public class SmsRecord extends BaseEntity implements Serializable {

    /**
     * 内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 电话
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 短信平台
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