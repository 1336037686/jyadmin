package com.jyadmin.system.config.detail.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jyadmin.domain.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 系统配置信息
 * @TableName sys_config_detail
 */
@TableName(value ="sys_config_detail")
@Data
public class ConfigDetail extends BaseEntity implements Serializable {

    /**
     * 配置名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 配置编码
     */
    @TableField(value = "code")
    private String code;

    /**
     * 使用模板ID sys_config_template
     */
    @TableField(value = "template_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long templateId;

    /**
     * 参数数据
     */
    @TableField(value = "data")
    private String data;

    /**
     * 是否启用 0=不启用，1=启用
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 配置模板JSON转换为对象
     */
    @TableField(exist = false)
    private List<ConfigDetailJsonModel> jsonObjs;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}