package com.jyadmin.generate.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jyadmin.generate.domain.CodeGenerateField;
import com.jyadmin.generate.domain.CodeGenerateFieldConfig;
import com.jyadmin.generate.domain.CodeGenerateTable;
import com.jyadmin.generate.domain.CodeGenerateTableConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-05-12 16:33 <br>
 * @description: UserConfigReqVO <br>
 */
@ApiModel("代码生成器-用户设置查看-数据模型")
@Data
@Accessors(chain = true)
public class UserConfigResVO {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long tableId;

    /**
     * 表元数据
     */
    @ApiModelProperty(value = "表元数据", name = "table")
    private TableExtend table;

    /**
     * 字段配置
     */
    @ApiModelProperty(value = "字段配置", name = "fieldConfigs")
    private List<FieldExtend> fields;

    /**
     * 表信息
     */
    @ApiModel("代码生成器-用户设置查看-TableExtend-数据模型")
    @Data
    @Accessors(chain = true)
    public static class TableExtend {

        @ApiModelProperty(value = "表数据", name = "table")
        private CodeGenerateTable table;

        @ApiModelProperty(value = "表配置", name = "tableConfig")
        private CodeGenerateTableConfig tableConfig;
    }

    /**
     * 字段信息
     */
    @ApiModel("代码生成器-用户设置查看-FieldExtend-数据模型")
    @Data
    @Accessors(chain = true)
    public static class FieldExtend {

        @ApiModelProperty(value = "属性信息", name = "field")
        private CodeGenerateField field;

        @ApiModelProperty(value = "属性配置", name = "fieldConfig")
        private CodeGenerateFieldConfig fieldConfig;
    }

}
