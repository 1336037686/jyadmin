package com.jyadmin.generate.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-05-20 23:28 <br>
 * @description: CodePreviewResVO <br>
 */
@Data
@Accessors(chain = true)
public class CodePreviewResVO {

    private String javaControllerCode;

    private String javaServiceCode;

    private String javaServiceImplCode;

    private String javaMapperCode;

    private String javaMapperXMLCode;

    private String vueIndexCode;

    private String vueFormCode;

    private String vueDetailCode;

    private String vueApiCode;

}
