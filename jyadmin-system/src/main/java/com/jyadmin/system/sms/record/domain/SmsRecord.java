package com.jyadmin.system.sms.record.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 短信发送记录
 * @TableName sys_sms_record
 */
@TableName(value ="sys_sms_record")
@Data
public class SmsRecord implements Serializable {
    /**
     * id
     */
    @TableField(value = "id")
    private String id;

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

    /**
     * 创建人
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 更新人
     */
    @TableField(value = "update_by")
    private String updateBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    /**
     * 是否删除 0:=未删除 1=删除
     */
    @TableField(value = "deleted")
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}