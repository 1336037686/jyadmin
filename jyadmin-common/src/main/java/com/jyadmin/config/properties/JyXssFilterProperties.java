package com.jyadmin.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * XSS过滤器配置
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-07-04 22:57 <br>
 * @description: JyXssFilterProperties <br>
 */
@ConfigurationProperties("jyadmin.safe.xss")
@Component
@Data
public class JyXssFilterProperties {

    /**
     * 免XSS过滤方法全类名
     * 如 com.jyadmin.module.category.controller.CategoryController.doCreate
     */
    private List<String> ignoreMethod = new ArrayList<>();

}
