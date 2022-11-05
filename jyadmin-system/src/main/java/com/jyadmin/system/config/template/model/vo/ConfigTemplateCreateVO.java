package com.jyadmin.system.config.template.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统配置模板
 * @TableName sys_config_template
 */
@ApiModel("系统配置模板-新增-数据模型")
@Data
public class ConfigTemplateCreateVO implements Serializable {

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