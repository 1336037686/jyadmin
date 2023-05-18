package com.jyadmin.generate.model.dto;

import cn.hutool.extra.template.Template;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 代码生成元数据
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-05-18 22:33 <br>
 * @description: CodeGenerateMetaDataDTO <br>
 */
@Data
@Accessors(chain = true)
public class CodeGenerateMetaDataDTO {

    private Template template;

    private String parentPath;

}
