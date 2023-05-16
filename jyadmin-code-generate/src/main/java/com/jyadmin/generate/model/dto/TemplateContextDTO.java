package com.jyadmin.generate.model.dto;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-05-15 11:14 <br>
 * @description: TemplateContextDTO <br>
 */
@Data
@Accessors(chain = true)
public class TemplateContextDTO {

    /**
     * 模板配置信息
     */
    private TemplateConfig config;

    /**
     * 模板数据
     */
    private TemplateModelDTO model;
}
