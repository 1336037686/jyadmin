package com.jyadmin.log.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 用户登录日志
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-12-20 23:51 <br>
 * @description: UserLoginLog <br>
 */
@Data
@Accessors(chain = true)
public class UserLoginLog {

    /**
     * ID
     */
    @TableId(value = "id")
    private String id;

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
     * 请求时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField(value = "request_time")
    private LocalDateTime requestTime;


}
