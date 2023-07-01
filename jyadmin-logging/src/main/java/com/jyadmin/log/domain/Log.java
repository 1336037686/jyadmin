package com.jyadmin.log.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jyadmin.domain.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统操作日志表
 * @TableName sys_log
 */
@TableName(value ="sys_log")
@Data
@Accessors(chain = true)
public class Log extends BaseEntity implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 操作名称
     */
    @TableField(value = "handle_name")
    private String handleName;

    /**
     * 操作描述
     */
    @TableField(value = "handle_desc")
    private String handleDesc;

    /**
     * ip地址
     */
    @TableField(value = "ip_address")
    private String ipAddress;

    /**
     * 所属地区
     */
    @TableField(value = "ip_area")
    private String ipArea;

    /**
     * 浏览器
     */
    @TableField(value = "browser")
    private String browser;

    /**
     * 设备
     */
    @TableField(value = "application")
    private String application;

    /**
     * 请求路径
     */
    @TableField(value = "request_path")
    private String requestPath;

    /**
     * 请求类型
     */
    @TableField(value = "request_method")
    private String requestMethod;

    /**
     * 请求参数
     */
    @TableField(value = "request_param")
    private String requestParam;

    /**
     * 请求时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField(value = "request_time")
    private LocalDateTime requestTime;

    /**
     * 请求类方法
     */
    @TableField(value = "method")
    private String method;

    /**
     * 执行状态
     */
    @TableField(value = "execute_status")
    private Integer executeStatus;

    /**
     * 执行耗时
     */
    @TableField(value = "execute_time")
    private Integer executeTime;

    /**
     * 返回数据
     */
    @TableField(value = "response_data")
    private String responseData;

    /**
     * 异常内容
     */
    @TableField(value = "error_desc")
    private String errorDesc;

    /**
     * 操作用户
     */
    @TableField(value = "username")
    private String username;


}