package com.jyadmin.system.datadict.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jyadmin.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 通用数据字典
 * @TableName sys_simple_data_dict
 */
@TableName(value ="sys_simple_data_dict")
@Data
public class SimpleDataDict extends BaseEntity implements Serializable {
    /**
     * ID
     */
    @TableField(value = "id")
    private String id;

    /**
     * 字典名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 字典编码
     */
    @TableField(value = "code")
    private String code;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}