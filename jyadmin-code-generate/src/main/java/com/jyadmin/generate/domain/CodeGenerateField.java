package com.jyadmin.generate.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jyadmin.domain.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 代码生成器-表字段信息
 * @TableName code_generate_field
 */
@TableName(value ="code_generate_field")
@Accessors(chain = true)
@Data
public class CodeGenerateField extends BaseEntity implements Serializable {

    /**
     * 表ID
     */
    @TableField(value = "table_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long tableId;

    /**
     * 字段名称
     */
    @TableField(value = "field_name")
    private String fieldName;

    /**
     * 字段类型
     */
    @TableField(value = "field_type")
    private String fieldType;

    /**
     * 字段长度
     */
    @TableField(value = "field_length")
    private String fieldLength;

    /**
     * 字段小数点
     */
    @TableField(value = "field_decimal_digits")
    private String fieldDecimalDigits;

    /**
     * 是否非空 1=不能为空 0=可以为空
     */
    @TableField(value = "non_null")
    private String nonNull;

    /**
     * 是否为主键 1=是 0=不是
     */
    @TableField(value = "pk")
    private String pk;

    /**
     * 是否为自增 1=是 0=不是
     */
    @TableField(value = "auto_incre")
    private String autoIncre;

    /**
     * 默认值
     */
    @TableField(value = "default_value")
    private String defaultValue;

    /**
     * 备注
     */
    @TableField(value = "field_remark")
    private String fieldRemark;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}