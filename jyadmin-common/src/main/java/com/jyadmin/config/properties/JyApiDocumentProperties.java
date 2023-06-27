package com.jyadmin.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Api文档配置
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-07-04 22:30 <br>
 * @description: JyApiDocumentProperties <br>
 */
@ConfigurationProperties(prefix = JyConfigItemConstant.API_DOC)
@Component
@Data
public class JyApiDocumentProperties {

    /**
     * 描述
     */
    private String description = "jyadmin API文档";

    /**
     * 更新服务条款url
     */
    private String termsOfServiceUrl = "";

    /**
     * API负责人的联系信息
     */
    private String contact = "jyadmin";

    /**
     * 版本
     */
    private String version = "v0.1.0";

    /**
     * 分组名称
     */
    private String groupName = "默认";

    /**
     * 基础扫描包路径
     */
    private String basePackage = "com.jyadmin";

}
