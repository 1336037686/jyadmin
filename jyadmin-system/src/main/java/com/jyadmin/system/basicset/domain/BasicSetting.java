package com.jyadmin.system.basicset.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jyadmin.domain.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统基础设置
 * @TableName sys_basic_setting
 */
@TableName(value ="sys_basic_setting")
@Data
public class BasicSetting extends BaseEntity implements Serializable {

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
     * 配置值
     */
    @TableField(value = "value")
    private String value;

    /**
     * 配置类型
     */
    @TableField(value = "type")
    private String type;

    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}