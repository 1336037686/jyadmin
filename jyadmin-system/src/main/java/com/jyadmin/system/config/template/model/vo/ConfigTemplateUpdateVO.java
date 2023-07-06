package com.jyadmin.system.config.template.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 系统配置模板
 * @TableName sys_config_template
 */
@ApiModel("系统配置模板-修改-数据模型")
@Data
public class ConfigTemplateUpdateVO implements Serializable {

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
     * 配置模板JSON
     */
    @ApiModelProperty(value = "配置模板JSON", name = "template")
    @NotBlank(message = "配置模板JSON不能为空")
    private String template;


}