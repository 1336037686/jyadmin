package com.jyadmin.generate.model.vo;

import com.jyadmin.domain.base.BasePageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-05-11 10:29 <br>
 * @description: TableQueryVO <br>
 */
@ApiModel("代码生成器-查询-表列表-数据模型")
@Data
public class TableQueryReqVO extends BasePageVO {

    /**
     * 表名称
     */
    @ApiModelProperty(value = "表名称", name = "tableName")
    private String tableName;

}
