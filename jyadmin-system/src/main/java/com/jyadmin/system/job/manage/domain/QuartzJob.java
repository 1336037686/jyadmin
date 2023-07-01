package com.jyadmin.system.job.manage.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jyadmin.domain.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统定时任务
 * @TableName sys_quartz_job
 */
@TableName(value ="sys_quartz_job")
@Data
public class QuartzJob extends BaseEntity implements Serializable {

    @JsonIgnore
    public static final String JOB_KEY = "sys_job";

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
     * 任务状态（1=执行，0= 暂停）
     */
    @TableField(value = "job_status")
    private Integer jobStatus;

    /**
     * 执行状态（0=无异常，1=异常暂停）
     */
    @TableField(value = "run_status")
    private Integer runStatus;

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
     * 是否开启异常告警（0=不开启，1=开启）
     */
    @TableField(value = "is_alarm")
    private Integer isAlarm;

    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}