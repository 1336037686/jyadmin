package com.jyadmin.system.config.template.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jyadmin.domain.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 系统配置模板
 * @TableName sys_config_template
 */
@TableName(value ="sys_config_template")
@Data
public class ConfigTemplate extends BaseEntity implements Serializable {

    /**
     * 配置模板名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 配置模板编码
     */
    @TableField(value = "code")
    private String code;

    /**
     * 配置模板JSON
     */
    @TableField(value = "template")
    private String template;

    /**
     * 配置模板JSON转换为对象
     */
    @TableField(exist = false)
    private List<ConfigTemplateJsonModel> jsonObjs;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}