package com.jyadmin.generate.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.jyadmin.generate.domain.CodeGenerateField;
import com.jyadmin.generate.domain.CodeGenerateFieldConfig;
import com.jyadmin.generate.domain.CodeGenerateTable;
import com.jyadmin.generate.domain.CodeGenerateTableConfig;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 数据模型
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-05-13 19:15 <br>
 * @description: TemplateModelDTO <br>
 */
@Data
@Accessors(chain = true)
public class TemplateModelDTO {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 备注
     */
    private String tableRemark;

    /**
     * 存储引擎
     */
    private String tableEngine;

    /**
     * 字符集
     */
    private String tableCharset;

    /**
     * 排序规则
     */
    private String tableOrder;

    /**
     * 表创建DDL
     */
    private String tableDdl;

    /**
     * 表名小驼峰命名（首字母小写），去除前缀后缀
     * helloWorld
     */
    private String realTableNameLowCamelCase;

    /**
     * 表名大驼峰命名(首字母大写)，去除前缀后缀
     * HelloWorld
     */
    private String realTableNameUpperCamelCase;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 需要导入包
     */
    private List<String> importPackages;

    /**
     * 类名
     */
    private String className;

    /**
     * 作者
     */
    private String author;

    /**
     * 当前时间
     */
    private String currTime;

    /**
     * 描述
     */
    private String description;

    /**
     * 版本
     */
    private String version;

    /**
     * 表类型 ot=普通表，rt=关联中间表
     */
    private String tableType;

    /**
     * 后端公共 URL 前缀
     */
    private String publicUrl;

    /**
     * swagger API接口名称 @Api 的 value
     */
    private String swaggerApiValue;

    /**
     * swagger API接口分类 @Api 的 tags
     */
    private String swaggerApiTag;

    /**
     * 前端页面文件生成路径
     */
    private String pageViewPath;

    /**
     * 字段信息
     */
    private List<FieldTemplateModel> fields;


    /**
     * 属性数据模型
     */
    @Data
    @Accessors(chain = true)
    public static class FieldTemplateModel {

        /**
         * 字段名称
         */
        private String fieldName;

        /**
         * 表名小驼峰命名（首字母小写），去除前缀后缀
         * helloWorld
         */
        private String realFieldNameLowCamelCase;

        /**
         * 表名大驼峰命名(首字母大写)，去除前缀后缀
         * HelloWorld
         */
        private String realFieldNameUpperCamelCase;

        /**
         * 字段类型
         */
        private String fieldTypeName;

        /**
         * 字段类型
         */
        private String fieldType;

        /**
         * 字段长度
         */
        private String fieldLength;

        /**
         * 字段小数点
         */
        private String fieldDecimalDigits;

        /**
         * 是否非空 1=不能为空 0=可以为空
         */
        private String nonNull;

        /**
         * 是否为主键 1=是 0=不是
         */
        private String pk;

        /**
         * 是否为自增 1=是 0=不是
         */
        private String autoIncre;

        /**
         * 默认值
         */
        private String defaultValue;

        /**
         * 备注
         */
        private String fieldRemark;

        /**
         * 排序
         */
        private Integer sort;

        /**
         * 属性对应的Java类型
         */
        private String javaType;

        /**
         * 属性对应的Java类型全类名
         */
        private String className;

        /**
         * 别名，用于在表单和列表展示的名称
         */
        private String fieldAlias;

        /**
         * 是否在列表展示 0=不展示 1=展示
         */
        private Integer showPage;

        /**
         * 是否在详情上展示  0=不展示 1=展示
         */
        private Integer showDetail;

        /**
         * 是否在表单展示 0=不展示 1=展示
         */
        private Integer showForm;

        /**
         * 是否必填 0=不必填 1=必填
         */
        private Integer formRequire;

        /**
         * 表单类型
         */
        private String formType;

        /**
         * 表单字典类型
         */
        private String formDict;

        /**
         * 查询方式
         */
        private String formSelectMethod;

        /**
         * 是否在查询展示 0=不展示 1=展示
         */
        private Integer showQuery;

        /**
         * 是否忽略字段 0=不是 1=是
         */
        private Integer fieldIgnore;

    }

}
