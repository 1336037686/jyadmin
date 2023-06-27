package com.jyadmin.system.config.detail.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import com.jyadmin.domain.base.BaseEntity;
import lombok.Data;

/**
 * 系统配置信息
 * @TableName sys_config_detail
 */
@TableName(value ="sys_config_detail")
@Data
public class ConfigDetail extends BaseEntity implements Serializable {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

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
    private String templateId;

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