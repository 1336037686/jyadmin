package com.jyadmin.generate.model.vo;

import com.jyadmin.generate.domain.CodeGenerateField;
import com.jyadmin.generate.domain.CodeGenerateFieldConfig;
import com.jyadmin.generate.domain.CodeGenerateTable;
import com.jyadmin.generate.domain.CodeGenerateTableConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-05-12 16:33 <br>
 * @description: UserConfigReqVO <br>
 */
@ApiModel("代码生成器-用户设置-数据模型")
@Data
public class UserConfigReqVO {

    private String tableId;

    /**
     * 表信息
     */
    @ApiModelProperty(value = "表信息", name = "table")
    private CodeGenerateTable table;

    /**
     * 表配置
     */
    @ApiModelProperty(value = "表配置", name = "tableConfig")
    private CodeGenerateTableConfig tableConfig;

    /**
     * 字段信息
     */
    @ApiModelProperty(value = "字段信息", name = "fields")
    private List<CodeGenerateField> fields;

    /**
     * 字段配置
     */
    @ApiModelProperty(value = "字段配置", name = "fieldConfigs")
    private List<CodeGenerateFieldConfig> fieldConfigs;

}
