package com.jyadmin.system.config.module.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jyadmin.domain.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统基础配置（各个模块单独配置）
 * @TableName sys_module_config
 */
@TableName(value ="sys_module_config")
@Data
public class ModuleConfig extends BaseEntity implements Serializable {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 托管平台（字典）
     */
    @TableField(value = "storage_type")
    private String storageType;

    /**
     * 相应配置
     */
    @TableField(value = "config")
    private String config;

    /**
     * 所属模块
     */
    @TableField(value = "module")
    private String module;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}