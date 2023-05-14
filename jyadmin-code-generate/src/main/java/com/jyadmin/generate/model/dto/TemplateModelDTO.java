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
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-05-13 19:15 <br>
 * @description: TemplateModelDTO <br>
 */
@Data
@Accessors(chain = true)
public class TemplateModelDTO {

    @Data
    @Accessors(chain = true)
    public static class JavaDomainClassTemplateModel {
        /**
         * 元数据
         */
        private CodeGenerateTable metaTable;
        private CodeGenerateTableConfig metaTableConfig;

        /**
         * 表名
         */
        private String tableName;

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
         * swagger API接口名称 @Api 的 value
         */
        private String swaggerApiValue;

        /**
         * swagger API接口分类 @Api 的 tags
         */
        private String swaggerApiTag;

        /**
         * 后端公共 URL 前缀
         */
        private String publicUrl;

        /**
         * 类名
         */
        private String className;

        /**
         * 备注
         */
        private String tableRemark;

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
         * 字段信息
         */
        private List<JavaDomainFieldTemplateModel> fields;

    }

    @Data
    @Accessors(chain = true)
    public static class JavaDomainFieldTemplateModel {
        /**
         * 元数据
         */
        private CodeGenerateField metaFields;
        private CodeGenerateFieldConfig metaConfigs;

        /**
         * 表名
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
         * 是否忽略字段
         */
        private Boolean fieldIgnore;

    }

}
