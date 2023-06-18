package com.jyadmin.generate.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 代码生成器配置
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-05-13 11:04 <br>
 * @description: CodeGenerateConfig <br>
 */
@Configuration
@ConfigurationProperties(prefix = "jyadmin.code-generate")
@Data
public class CodeGenerateConfig {

    /**
     * 数据库名称
     */
    private String dbName;

}
