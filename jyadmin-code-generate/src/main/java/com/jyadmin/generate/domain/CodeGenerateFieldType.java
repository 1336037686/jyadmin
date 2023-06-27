package com.jyadmin.generate.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.jyadmin.domain.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * @TableName code_generate_field_type
 */
@TableName(value ="code_generate_field_type")
@Accessors(chain = true)
@Data
public class CodeGenerateFieldType extends BaseEntity implements Serializable {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 数据库类型
     */
    @TableField(value = "jdbc_type")
    private String jdbcType;

    /**
     * 对应Java类型
     */
    @TableField(value = "java_type")
    private String javaType;

    /**
     * 全类名
     */
    @TableField(value = "class_name")
    private String className;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}