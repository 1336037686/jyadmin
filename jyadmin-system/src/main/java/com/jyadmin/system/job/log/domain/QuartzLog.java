package com.jyadmin.system.job.log.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统定时任务日志
 * @TableName sys_quartz_log
 */
@TableName(value ="sys_quartz_log")
@Data
public class QuartzLog implements Serializable {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;


    /**
     * jobId 任务ID sys_quartz_job id
     */
    @TableField(value = "job_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long jobId;

    /**
     * 任务编号
     */
    @TableField(value = "code")
    private String code;

    /**
     * 任务名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 定时任务类
     */
    @TableField(value = "bean")
    private String bean;

    /**
     * 定时任务方法
     */
    @TableField(value = "method")
    private String method;

    /**
     * 参数(JSON)
     */
    @TableField(value = "params")
    private String params;

    /**
     * cron表达式
     */
    @TableField(value = "cron_expression")
    private String cronExpression;

    /**
     * 执行状态（1=执行成功，0= 执行异常）
     */
    @TableField(value = "exec_status")
    private Integer execStatus;

    /**
     * 执行耗时
     */
    @TableField(value = "exec_time")
    private Long execTime;

    /**
     * 异常内容
     */
    @TableField(value = "error_desc")
    private String errorDesc;

    /**
     * 负责人
     */
    @TableField(value = "principal")
    private String principal;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 是否通知（0=未通知负责人，1=通知负责人）
     */
    @TableField(value = "is_notify")
    private Integer isNotify;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 是否删除
     */
    @JsonIgnore
    @TableField(value = "deleted")
    @TableLogic
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}