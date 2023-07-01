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
 * 代码生成器-表字段配置
 * @TableName code_generate_field_config
 */
@TableName(value ="code_generate_field_config")
@Accessors(chain = true)
@Data
public class CodeGenerateFieldConfig extends BaseEntity implements Serializable {

    /**
     * 属性ID
     */
    @TableField(value = "field_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long fieldId;

    /**
     * 属性对应的Java类型
     */
    @TableField(value = "java_type")
    private String javaType;

    /**
     * 属性对应的Java类型全类名
     */
    @TableField(value = "class_name")
    private String className;

    /**
     * 别名，用于在表单和列表展示的名称
     */
    @TableField(value = "field_alias")
    private String fieldAlias;

    /**
     * 是否在列表展示 0=不展示 1=展示
     */
    @TableField(value = "show_page")
    private Integer showPage;

    /**
     * 是否在详情上展示  0=不展示 1=展示
     */
    @TableField(value = "show_detail")
    private Integer showDetail;

    /**
     * 是否在表单展示 0=不展示 1=展示
     */
    @TableField(value = "show_form")
    private Integer showForm;

    /**
     * 是否必填 0=不必填 1=必填
     */
    @TableField(value = "form_require")
    private Integer formRequire;

    /**
     * 表单类型
     */
    @TableField(value = "form_type")
    private String formType;

    /**
     * 表单字典类型
     */
    @TableField(value = "form_dict")
    private String formDict;

    /**
     * 查询方式
     */
    @TableField(value = "form_select_method")
    private String formSelectMethod;

    /**
     * 是否在查询展示 0=不展示 1=展示
     */
    @TableField(value = "show_query")
    private Integer showQuery;

    /**
     * 是否忽略字段 0=不是 1=是
     */
    @TableField(value = "field_ignore")
    private Integer fieldIgnore;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}