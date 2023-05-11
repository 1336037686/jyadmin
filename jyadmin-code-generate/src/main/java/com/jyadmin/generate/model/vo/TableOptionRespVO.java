package com.jyadmin.generate.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-05-11 10:33 <br>
 * @description: SimpleTableOptionsVO <br>
 */
@ApiModel("代码生成器-查询-表列表-数据模型")
@Accessors(chain = true)
@Data
public class TableOptionRespVO {

    /**
     * 表名称
     */
    @ApiModelProperty(value = "表名称", name = "tableName")
    private String tableName;

    /**
     * 表备注
     */
    @ApiModelProperty(value = "表备注", name = "tableRemark")
    private String tableRemark;

}
