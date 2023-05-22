package com.jyadmin.generate.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Java 类型 VO
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-05-22 18:45 <br>
 * @description: CodeGenerateJavaFieldTypeResVO <br>
 */
@Data
@Accessors(chain = true)
public class CodeGenerateJavaFieldTypeResVO {

    private String javaType;

    private String className;

}
