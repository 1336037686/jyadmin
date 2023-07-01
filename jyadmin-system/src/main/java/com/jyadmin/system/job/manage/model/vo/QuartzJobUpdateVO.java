package com.jyadmin.system.job.manage.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-09 15:54 <br>
 * @description: TagCreateVO <br>
 */
@ApiModel("系统定时任务-修改-数据模型")
@Data
public class QuartzJobUpdateVO implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", name = "id")
    @NotNull(message = "ID不能为空")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 任务编号
     */
    @ApiModelProperty(value = "任务编号", name = "code")
    @NotBlank(message = "任务编号不能为空")
    private String code;

    /**
     * 任务名称
     */
    @ApiModelProperty(value = "任务名称", name = "name")
    @NotBlank(message = "任务名称不能为空")
    private String name;

    /**
     * 定时任务类
     */
    @ApiModelProperty(value = "定时任务类", name = "bean")
    @NotBlank(message = "定时任务类不能为空")
    private String bean;

    /**
     * 定时任务方法
     */
    @ApiModelProperty(value = "定时任务方法", name = "method")
    @NotBlank(message = "定时任务方法不能为空")
    private String method;

    /**
     * 参数(JSON)
     */
    @ApiModelProperty(value = "参数", name = "params")
    private String params;

    /**
     * cron表达式
     */
    @ApiModelProperty(value = "cron表达式", name = "cronExpression")
    @NotBlank(message = "cron表达式不能为空")
    private String cronExpression;

    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人", name = "principal")
    @NotBlank(message = "负责人不能为空")
    private String principal;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱", name = "email")
    private String email;

    /**
     * 是否开启异常告警（0=不开启，1=开启）
     */
    @ApiModelProperty(value = "是否开启异常告警", name = "isAlarm")
    @NotNull(message = "是否开启异常告警不能为空")
    private Integer isAlarm;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述", name = "description")
    private String description;


}
