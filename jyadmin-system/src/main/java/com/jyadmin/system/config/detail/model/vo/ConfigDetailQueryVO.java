package com.jyadmin.system.config.detail.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jyadmin.domain.base.BasePageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 系统配置信息
 * @TableName sys_config_template
 */
@ApiModel("系统配置信息-查找-数据模型")
@Data
public class ConfigDetailQueryVO extends BasePageVO implements Serializable {

    /**
     * 配置模板名称
     */
    @ApiModelProperty(value = "配置模板名称", name = "name")
    private String name;

    /**
     * 配置模板编码
     */
    @ApiModelProperty(value = "配置模板编码", name = "code")
    private String code;

    /**
     * 使用模板ID
     */
    @ApiModelProperty(value = "使用模板ID", name = "templateId")
    @NotNull(message = "使用模板ID不能为空")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long templateId;


}