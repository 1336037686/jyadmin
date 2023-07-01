package com.jyadmin.system.config.detail.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 系统配置信息
 * @TableName sys_config_template
 */
@ApiModel("系统配置信息-修改-数据模型")
@Data
public class ConfigDetailUpdateVO implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", name = "id")
    @NotNull(message = "ID不能为空")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 配置模板名称
     */
    @ApiModelProperty(value = "配置模板名称", name = "name")
    @NotBlank(message = "配置模板名称不能为空")
    private String name;

    /**
     * 配置模板编码
     */
    @ApiModelProperty(value = "配置模板编码", name = "code")
    @NotBlank(message = "配置模板编码不能为空")
    private String code;

    /**
     * 使用模板ID
     */
    @ApiModelProperty(value = "使用模板ID", name = "templateId")
    @NotNull(message = "使用模板ID不能为空")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long templateId;

    /**
     * 配置模板JSON
     */
    @ApiModelProperty(value = "参数数据", name = "data")
    @NotBlank(message = "参数数据不能为空")
    private String data;

    /**
     * 是否启用 0=不启用，1=启用
     */
    @ApiModelProperty(value = "是否启用", name = "status")
    @NotNull(message = "是否启用不能为空")
    private Integer status;

}