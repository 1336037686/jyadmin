package com.jyadmin.generate.common.constant;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * 代码生成器常量类
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-05-11 16:14 <br>
 * @description: CodeGenerateConstant <br>
 */
public class CodeGenerateConstant {

    public static final String TEMPLATE_JAVA_VM_PATH = "template" + FileUtil.FILE_SEPARATOR + "java";
    public static final String TEMPLATE_VUE_VM_PATH = "template" + FileUtil.FILE_SEPARATOR + "vue";

    public static final String TABLE_CONFIG_Author = "jyadmin code generate";
    public static final String TABLE_CONFIG_PACKAGE_NAME_PREFIX = "com.jyadmin.generate";
    public static final String TABLE_CONFIG_PUBLIC_URL = "/api";
    public static final String TABLE_CONFIG_SWAGGER_API_TAG_PREFIX = "系统：";
    public static final String TABLE_CONFIG_TYPE = "ot"; // 默认的数据库表类型为普通表
    public static final String TABLE_CONFIG_VERSION = "1.0"; // 默认的数据库表类型为普通表

    public static final Integer FIELD_CONFIG_SHOW_PAGE = 1;
    public static final Integer FIELD_CONFIG_SHOW_DETAIL = 1;
    public static final Integer FIELD_CONFIG_SHOW_FORM = 1;
    public static final Integer FIELD_CONFIG_SHOW_QUERY = 1;
    public static final Integer FIELD_CONFIG_REQUIRE = 1;
    public static final String FIELD_CONFIG_FORM_TYPE = "form_type_input";
    public static final String FIELD_CONFIG_FORM_SELECT_METHOD = "form_select_method_eq";

    public static final String JAVA_SOURCE_CODE_SRC_PATH = "src";
    public static final String JAVA_SOURCE_CODE_RESOURCE_PATH = "resources";

    public static final List<String> IGNORE_FIELDS = Lists.newArrayList(
            "create_by", "update_by", "create_time", "update_time", "deleted"
    );

    public static final String RESPONSE_ZIP_FILE_NAME = "code-generate.zip";


    /**
     * status枚举类 1=启用 0=禁用
     */
    public enum EnableStatus {
        YES("显示", 1),
        NO("不显示", 0);

        // 名称
        private String name;
        // 值
        private Integer value;

        public String getName() {
            return name;
        }

        public Integer getValue() {
            return value;
        }

        EnableStatus(String name, Integer value) {
            this.name = name;
            this.value = value;
        }
    }

}
