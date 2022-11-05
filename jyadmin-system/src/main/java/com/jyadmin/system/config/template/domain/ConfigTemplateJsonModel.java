package com.jyadmin.system.config.template.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 模板JSON字段
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-06 00:48 <br>
 * @description: ConfigTemplateJsonModel <br>
 */
@Data
@Accessors(chain = true)
public class ConfigTemplateJsonModel {

    /**
     * 字段名称
     */
    private String name;

    /**
     * 字段编码
     */
    private String code;

    /**
     * 字段类型
     */
    private String type;

    /**
     * 默认缺省值
     */
    private String defaultValue;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 注释
     */
    private String comment;


}
