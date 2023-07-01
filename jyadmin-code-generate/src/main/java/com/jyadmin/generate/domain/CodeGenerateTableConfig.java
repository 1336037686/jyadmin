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
 * 代码生成器-数据库表配置
 * @TableName code_generate_table_config
 */
@TableName(value ="code_generate_table_config")
@Accessors(chain = true)
@Data
public class CodeGenerateTableConfig extends BaseEntity implements Serializable {

    /**
     * 表ID
     */
    @TableField(value = "table_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long tableId;

    /**
     * 表类型 ot=普通表，rt=关联中间表
     */
    @TableField(value = "table_type")
    private String tableType;

    /**
     * 作者
     */
    @TableField(value = "author")
    private String author;

    /**
     * 后端代码包路径
     */
    @TableField(value = "package_name")
    private String packageName;

    /**
     * 后端公共 URL 前缀
     */
    @TableField(value = "public_url")
    private String publicUrl;

    /**
     * 后端去除表的前缀字符
     */
    @TableField(value = "remove_table_prefix")
    private String removeTablePrefix;

    /**
     * 后端去除表的后缀字符
     */
    @TableField(value = "remove_table_suffix")
    private String removeTableSuffix;

    /**
     * 后端去除属性字段的前缀字符
     */
    @TableField(value = "remove_field_prefix")
    private String removeFieldPrefix;

    /**
     * 后端去除属性字段的后缀字符
     */
    @TableField(value = "remove_field_suffix")
    private String removeFieldSuffix;

    /**
     * swagger API接口名称 @Api 的 value
     */
    @TableField(value = "swagger_api_value")
    private String swaggerApiValue;

    /**
     * swagger API接口分类 @Api 的 tags
     */
    @TableField(value = "swagger_api_tag")
    private String swaggerApiTag;

    /**
     * 前端页面文件生成路径
     */
    @TableField(value = "page_view_path")
    private String pageViewPath;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}