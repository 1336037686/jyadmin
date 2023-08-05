package com.jyadmin.log.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty("ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 操作用户
     */
    @ApiModelProperty("操作用户")
    private String username;

    /**
     * 操作名称
     */
    @ApiModelProperty("操作名称")
    private String handleName;

    /**
     * 操作描述
     */
    @ApiModelProperty("操作描述")
    private String handleDesc;

    /**
     * ip地址
     */
    @ApiModelProperty("ip地址")
    private String ipAddress;

    /**
     * 所属地区
     */
    @ApiModelProperty("所属地区")
    private String ipArea;

    /**
     * 浏览器
     */
    @ApiModelProperty("浏览器")
    private String browser;

    /**
     * 设备
     */
    @ApiModelProperty("设备")
    private String application;

    /**
     * 执行耗时
     */
    @ApiModelProperty("执行耗时")
    private Integer executeTime;

    /**
     * 请求时间
     */
    @ApiModelProperty("请求时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime requestTime;

    /**
     * 执行状态
     */
    @ApiModelProperty("执行状态")
    private Integer executeStatus;


}
