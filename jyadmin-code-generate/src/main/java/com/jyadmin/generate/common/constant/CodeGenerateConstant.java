package com.jyadmin.generate.common.constant;

import cn.hutool.core.io.FileUtil;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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

    public static final String JAVA_SOURCE_CODE_SRC_PATH = "java" + FileUtil.FILE_SEPARATOR + "src";
    public static final String JAVA_SOURCE_CODE_RESOURCE_PATH = "java" + FileUtil.FILE_SEPARATOR + "resources";
    public static final String VUE_SOURCE_CODE_VIEW_PATH = "vue" + FileUtil.FILE_SEPARATOR + "views" + FileUtil.FILE_SEPARATOR + "module";
    public static final String VUE_SOURCE_CODE_API_PATH = "vue" + FileUtil.FILE_SEPARATOR + "api" + FileUtil.FILE_SEPARATOR + "module";
    public static final String JAVA_TEMPLATE_NAME_PREFIX = "java";
    public static final String VUE_TEMPLATE_NAME_PREFIX = "vue";
    public static final List<String> IGNORE_FIELDS = Lists.newArrayList("create_by", "update_by", "create_time", "update_time", "deleted");

    public static final String TEMPLATE_FILE_TYPE_JAVA = "java";
    public static final String TEMPLATE_FILE_TYPE_VUE = "vue";
    public static final String TEMPLATE_FILE_TYPE_XML = "xml";
    public static final String TEMPLATE_FILE_TYPE_JS = "js";

    public static final String RESPONSE_ZIP_FILE_NAME = "code-generate.zip";

    /**
     * status枚举类 1=启用 0=禁用
     */
    @AllArgsConstructor
    @Getter
    public enum EnableStatus {
        YES("显示", 1),
        NO("不显示", 0);
        // 名称
        private String name;
        // 值
        private Integer value;
    }

    /**
     * 模板信息枚举类
     */
    @Getter
    @AllArgsConstructor
    public enum TemplateInfo {
        // java
        CONTROLLER("java.controller", TEMPLATE_FILE_TYPE_JAVA, TEMPLATE_JAVA_VM_PATH + FileUtil.FILE_SEPARATOR + "simple-controller.java.vm"),
        DOMAIN("java.domain", TEMPLATE_FILE_TYPE_JAVA, TEMPLATE_JAVA_VM_PATH + FileUtil.FILE_SEPARATOR + "simple-domain.java.vm"),
        VO_CREATE_REQ_VO("java.vo.createReqVO", TEMPLATE_FILE_TYPE_JAVA, TEMPLATE_JAVA_VM_PATH + FileUtil.FILE_SEPARATOR + "simple-createReqVO.java.vm"),
        VO_UPDATE_REQ_VO("java.vo.updateReqVO", TEMPLATE_FILE_TYPE_JAVA, TEMPLATE_JAVA_VM_PATH + FileUtil.FILE_SEPARATOR + "simple-updateReqVO.java.vm"),
        VO_QUERY_REQ_VO("java.vo.queryReqVO", TEMPLATE_FILE_TYPE_JAVA, TEMPLATE_JAVA_VM_PATH + FileUtil.FILE_SEPARATOR + "simple-queryReqVO.java.vm"),
        SERVICE("java.service", TEMPLATE_FILE_TYPE_JAVA, TEMPLATE_JAVA_VM_PATH + FileUtil.FILE_SEPARATOR + "simple-service.java.vm"),
        SERVICE_IMPL("java.serviceImpl", TEMPLATE_FILE_TYPE_JAVA, TEMPLATE_JAVA_VM_PATH + FileUtil.FILE_SEPARATOR + "simple-serviceImpl.java.vm"),
        MAPPER("java.mapper", TEMPLATE_FILE_TYPE_JAVA, TEMPLATE_JAVA_VM_PATH + FileUtil.FILE_SEPARATOR +  "simple-mapper.java.vm"),
        MAPPER_XML("java.mapper.xml", TEMPLATE_FILE_TYPE_XML, TEMPLATE_JAVA_VM_PATH + FileUtil.FILE_SEPARATOR + "simple-mapper.xml.vm"),
        // vue
        VUE_VIEW_INDEX("vue.view.index", TEMPLATE_FILE_TYPE_VUE, TEMPLATE_VUE_VM_PATH + FileUtil.FILE_SEPARATOR + "index.vue.vm"),
        VUE_VIEW_FORM("vue.view.form", TEMPLATE_FILE_TYPE_VUE, TEMPLATE_VUE_VM_PATH + FileUtil.FILE_SEPARATOR + "simple-form.vue.vm"),
        VUE_VIEW_DETAIL("vue.view.detail", TEMPLATE_FILE_TYPE_VUE, TEMPLATE_VUE_VM_PATH + FileUtil.FILE_SEPARATOR + "simple-detail.vue.vm"),
        VUE_VIEW_API("vue.view.api", TEMPLATE_FILE_TYPE_JS, TEMPLATE_VUE_VM_PATH + FileUtil.FILE_SEPARATOR + "simple-api.js.vm")
        ;

        // 模板名称
        private String name;
        // 文件类型
        private String type;
        // 模板路径
        private String templatePath;
    }

}
