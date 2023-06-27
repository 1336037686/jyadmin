package com.jyadmin.system.config.template.model.vo;

import com.jyadmin.domain.base.BasePageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统配置模板
 * @TableName sys_config_template
 */
@ApiModel("系统配置模板-查找-数据模型")
@Data
public class ConfigTemplateQueryVO extends BasePageVO implements Serializable {

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


}