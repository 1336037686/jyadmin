package com.jyadmin.generate.common.utils;

import cn.hutool.core.io.FileUtil;
import com.jyadmin.generate.common.constant.CodeGenerateConstant;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 生成代码元数据构建工具
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-05-18 17:18 <br>
 * @description: CodeGenerateMetaDataUtils <br>
 */
public class CodeGenerateMetaDataUtils {

    // 生成文件名
    private static final Map<String, Function<String, String>> CODE_GEN_FILE_NAME = new HashMap<>() {{
        // java
        put(CodeGenerateConstant.TemplateInfo.CONTROLLER.getName(), fileNamePrefix -> fileNamePrefix + "Controller.java");
        put(CodeGenerateConstant.TemplateInfo.DOMAIN.getName(), fileNamePrefix -> fileNamePrefix + ".java");
        put(CodeGenerateConstant.TemplateInfo.VO_CREATE_REQ_VO.getName(), fileNamePrefix -> fileNamePrefix + "CreateReqVO.java");
        put(CodeGenerateConstant.TemplateInfo.VO_UPDATE_REQ_VO.getName(), fileNamePrefix -> fileNamePrefix + "UpdateReqVO.java");
        put(CodeGenerateConstant.TemplateInfo.VO_QUERY_REQ_VO.getName(), fileNamePrefix -> fileNamePrefix + "QueryReqVO.java");
        put(CodeGenerateConstant.TemplateInfo.SERVICE.getName(), fileNamePrefix -> fileNamePrefix + "Service.java");
        put(CodeGenerateConstant.TemplateInfo.SERVICE_IMPL.getName(), fileNamePrefix -> fileNamePrefix + "ServiceImpl.java");
        put(CodeGenerateConstant.TemplateInfo.MAPPER.getName(), fileNamePrefix -> fileNamePrefix + "Mapper.java");
        put(CodeGenerateConstant.TemplateInfo.MAPPER_XML.getName(), fileNamePrefix -> fileNamePrefix + "Mapper.xml");
        // vue
        put(CodeGenerateConstant.TemplateInfo.VUE_VIEW_INDEX.getName(), fileNamePrefix -> "index.vue");
        put(CodeGenerateConstant.TemplateInfo.VUE_VIEW_FORM.getName(), fileNamePrefix -> fileNamePrefix + "-form.vue");
        put(CodeGenerateConstant.TemplateInfo.VUE_VIEW_DETAIL.getName(), fileNamePrefix -> fileNamePrefix + "-detail.vue");
        put(CodeGenerateConstant.TemplateInfo.VUE_VIEW_API.getName(), fileNamePrefix -> fileNamePrefix + "-api.js");
    }};

    // 文件生成代码父路径
    private static final Map<String, Function<String, String>> CODE_GEN_PARENT_PATH = new HashMap<>() {{
        // java
        put(CodeGenerateConstant.TemplateInfo.CONTROLLER.getName(), packagePath -> CodeGenerateConstant.JAVA_SOURCE_CODE_SRC_PATH + FileUtil.FILE_SEPARATOR + packagePath + FileUtil.FILE_SEPARATOR +"controller");
        put(CodeGenerateConstant.TemplateInfo.DOMAIN.getName(), packagePath -> CodeGenerateConstant.JAVA_SOURCE_CODE_SRC_PATH + FileUtil.FILE_SEPARATOR + packagePath + FileUtil.FILE_SEPARATOR +"domain");
        put(CodeGenerateConstant.TemplateInfo.VO_CREATE_REQ_VO.getName(), packagePath -> CodeGenerateConstant.JAVA_SOURCE_CODE_SRC_PATH + FileUtil.FILE_SEPARATOR + packagePath + FileUtil.FILE_SEPARATOR + "model" + FileUtil.FILE_SEPARATOR + "vo");
        put(CodeGenerateConstant.TemplateInfo.VO_UPDATE_REQ_VO.getName(), packagePath -> CodeGenerateConstant.JAVA_SOURCE_CODE_SRC_PATH + FileUtil.FILE_SEPARATOR + packagePath + FileUtil.FILE_SEPARATOR + "model" + FileUtil.FILE_SEPARATOR + "vo");
        put(CodeGenerateConstant.TemplateInfo.VO_QUERY_REQ_VO.getName(), packagePath -> CodeGenerateConstant.JAVA_SOURCE_CODE_SRC_PATH + FileUtil.FILE_SEPARATOR + packagePath + FileUtil.FILE_SEPARATOR + "model" + FileUtil.FILE_SEPARATOR + "vo");
        put(CodeGenerateConstant.TemplateInfo.SERVICE.getName(), packagePath -> CodeGenerateConstant.JAVA_SOURCE_CODE_SRC_PATH + FileUtil.FILE_SEPARATOR + packagePath + FileUtil.FILE_SEPARATOR + "service");
        put(CodeGenerateConstant.TemplateInfo.SERVICE_IMPL.getName(), packagePath -> CodeGenerateConstant.JAVA_SOURCE_CODE_SRC_PATH + FileUtil.FILE_SEPARATOR + packagePath + FileUtil.FILE_SEPARATOR + "service" + FileUtil.FILE_SEPARATOR + "impl");
        put(CodeGenerateConstant.TemplateInfo.MAPPER.getName(), packagePath -> CodeGenerateConstant.JAVA_SOURCE_CODE_SRC_PATH + FileUtil.FILE_SEPARATOR + packagePath + FileUtil.FILE_SEPARATOR + "mapper");
        put(CodeGenerateConstant.TemplateInfo.MAPPER_XML.getName(), packagePath -> CodeGenerateConstant.JAVA_SOURCE_CODE_RESOURCE_PATH + FileUtil.FILE_SEPARATOR + "mapper");
        // vue
        put(CodeGenerateConstant.TemplateInfo.VUE_VIEW_INDEX.getName(), packagePath -> CodeGenerateConstant.VUE_SOURCE_CODE_VIEW_PATH + FileUtil.FILE_SEPARATOR + packagePath);
        put(CodeGenerateConstant.TemplateInfo.VUE_VIEW_FORM.getName(), packagePath -> CodeGenerateConstant.VUE_SOURCE_CODE_VIEW_PATH + FileUtil.FILE_SEPARATOR + packagePath);
        put(CodeGenerateConstant.TemplateInfo.VUE_VIEW_DETAIL.getName(), packagePath -> CodeGenerateConstant.VUE_SOURCE_CODE_VIEW_PATH + FileUtil.FILE_SEPARATOR + packagePath);
        put(CodeGenerateConstant.TemplateInfo.VUE_VIEW_API.getName(), packagePath -> CodeGenerateConstant.VUE_SOURCE_CODE_API_PATH + FileUtil.FILE_SEPARATOR + packagePath);
    }};

    /**
     * 获取生成代码文件父路径
     * @param packagePath 包路径
     * @return /
     */
    public static String getCodeGenParentPath(String templateName, String packagePath) {
        return CODE_GEN_PARENT_PATH.get(templateName).apply(packagePath);
    }

    /**
     * 获取生成代码文件名
     * @return /
     */
    public static String getCodeGenFileName(String templateName, String fileNamePrefix) {
        return CODE_GEN_FILE_NAME.get(templateName).apply(fileNamePrefix);
    }
}
