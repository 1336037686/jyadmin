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

    public static final String JAVA_SOURCE_CODE_SRC_PATH = "src";
    public static final String JAVA_SOURCE_CODE_RESOURCE_PATH = "resources";

    public static final List<String> IGNORE_FIELDS = Lists.newArrayList(
            "create_by", "update_by", "create_time", "update_time", "deleted"
    );

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
    public enum TemplateInfo {
        CONTROLLER("controller", TEMPLATE_JAVA_VM_PATH + FileUtil.FILE_SEPARATOR + "simple-controller.java.vm"),
        DOMAIN("domain", TEMPLATE_JAVA_VM_PATH + FileUtil.FILE_SEPARATOR + "simple-domain.java.vm"),
        VO_CREATE_REQ_VO("vo.createReqVO", TEMPLATE_JAVA_VM_PATH + FileUtil.FILE_SEPARATOR + "simple-createReqVO.java.vm"),
        VO_UPDATE_REQ_VO("vo.updateReqVO", TEMPLATE_JAVA_VM_PATH + FileUtil.FILE_SEPARATOR + "simple-updateReqVO.java.vm"),
        VO_QUERY_REQ_VO("vo.queryReqVO", TEMPLATE_JAVA_VM_PATH + FileUtil.FILE_SEPARATOR + "simple-queryReqVO.java.vm"),
        SERVICE("service", TEMPLATE_JAVA_VM_PATH + FileUtil.FILE_SEPARATOR + "simple-service.java.vm"),
        SERVICE_IMPL("serviceImpl", TEMPLATE_JAVA_VM_PATH + FileUtil.FILE_SEPARATOR + "simple-serviceImpl.java.vm"),
        MAPPER("mapper", TEMPLATE_JAVA_VM_PATH + FileUtil.FILE_SEPARATOR +  "simple-mapper.java.vm"),
        MAPPER_XML("mapper.xml", TEMPLATE_JAVA_VM_PATH + FileUtil.FILE_SEPARATOR + "simple-mapper.xml.vm")
        ;

        // 模板名称
        private String name;

        // 模板路径
        private String templatePath;

        // 生成文件名
        private Map<String, Function<String, String>> codeGenFileName = new HashMap<>() {{
            put(TemplateInfo.CONTROLLER.getName(), fileNamePrefix -> fileNamePrefix + "Controller.java");
            put(TemplateInfo.DOMAIN.getName(), fileNamePrefix -> fileNamePrefix + ".java");
            put(TemplateInfo.VO_CREATE_REQ_VO.getName(), fileNamePrefix -> fileNamePrefix + "CreateReqVO.java");
            put(TemplateInfo.VO_UPDATE_REQ_VO.getName(), fileNamePrefix -> fileNamePrefix + "UpdateReqVO.java");
            put(TemplateInfo.VO_QUERY_REQ_VO.getName(), fileNamePrefix -> fileNamePrefix + "QueryReqVO.java");
            put(TemplateInfo.SERVICE.getName(), fileNamePrefix -> fileNamePrefix + "Service.java");
            put(TemplateInfo.SERVICE_IMPL.getName(), fileNamePrefix -> fileNamePrefix + "ServiceImpl.java");
            put(TemplateInfo.MAPPER.getName(), fileNamePrefix -> fileNamePrefix + "Mapper.java");
            put(TemplateInfo.MAPPER_XML.getName(), fileNamePrefix -> fileNamePrefix + "Mapper.xml");
        }};

        // 文件生成代码父路径
        private Map<String, Function<String, String>> codeGenParentPath = new HashMap<>() {{
            put(TemplateInfo.CONTROLLER.getName(), packagePath -> JAVA_SOURCE_CODE_SRC_PATH + FileUtil.FILE_SEPARATOR + packagePath + FileUtil.FILE_SEPARATOR +"controller");
            put(TemplateInfo.DOMAIN.getName(), packagePath -> JAVA_SOURCE_CODE_SRC_PATH + FileUtil.FILE_SEPARATOR + packagePath + FileUtil.FILE_SEPARATOR +"domain");
            put(TemplateInfo.VO_CREATE_REQ_VO.getName(), packagePath -> JAVA_SOURCE_CODE_SRC_PATH + FileUtil.FILE_SEPARATOR + packagePath + FileUtil.FILE_SEPARATOR + "model" + FileUtil.FILE_SEPARATOR + "vo");
            put(TemplateInfo.VO_UPDATE_REQ_VO.getName(), packagePath -> JAVA_SOURCE_CODE_SRC_PATH + FileUtil.FILE_SEPARATOR + packagePath + FileUtil.FILE_SEPARATOR + "model" + FileUtil.FILE_SEPARATOR + "vo");
            put(TemplateInfo.VO_QUERY_REQ_VO.getName(), packagePath -> JAVA_SOURCE_CODE_SRC_PATH + FileUtil.FILE_SEPARATOR + packagePath + FileUtil.FILE_SEPARATOR + "model" + FileUtil.FILE_SEPARATOR + "vo");
            put(TemplateInfo.SERVICE.getName(), packagePath -> JAVA_SOURCE_CODE_SRC_PATH + FileUtil.FILE_SEPARATOR + packagePath + FileUtil.FILE_SEPARATOR + "service");
            put(TemplateInfo.SERVICE_IMPL.getName(), packagePath -> JAVA_SOURCE_CODE_SRC_PATH + FileUtil.FILE_SEPARATOR + packagePath + FileUtil.FILE_SEPARATOR + "service" + FileUtil.FILE_SEPARATOR + "impl");
            put(TemplateInfo.MAPPER.getName(), packagePath -> JAVA_SOURCE_CODE_SRC_PATH + FileUtil.FILE_SEPARATOR + packagePath + FileUtil.FILE_SEPARATOR + "mapper");
            put(TemplateInfo.MAPPER_XML.getName(), packagePath -> JAVA_SOURCE_CODE_RESOURCE_PATH + FileUtil.FILE_SEPARATOR + "mapper");
        }};

        TemplateInfo(String name, String templatePath) {
            this.name = name;
            this.templatePath = templatePath;
        }

        /**
         * 获取生成代码文件父路径
         * @param packagePath 包路径
         * @return /
         */
        public String getCodeGenParentPath(String packagePath) {
            return this.codeGenParentPath.get(this.getName()).apply(packagePath);
        }

        /**
         * 获取生成代码文件名
         * @return /
         */
        public String getCodeGenFileName(String fileNamePrefix) {
            return this.codeGenFileName.get(this.getName()).apply(fileNamePrefix);
        }
    }

}
