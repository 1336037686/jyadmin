package com.jyadmin.generate.model.dto;

import cn.hutool.core.io.FileUtil;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-05-16 21:13 <br>
 * @description: TemplateConfig <br>
 */
@Data
@Accessors(chain = true)
public class TemplateConfig {

    /**
     * 基础名称
     */
    private String metaName;

}
