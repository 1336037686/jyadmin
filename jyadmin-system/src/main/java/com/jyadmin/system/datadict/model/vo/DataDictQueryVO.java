package com.jyadmin.system.datadict.model.vo;

import com.jyadmin.domain.base.BasePageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-07 22:06 <br>
 * @description: SysDataDictVO <br>
 */
@ApiModel("数据字典-查询-根节点-数据模型")
@Data
public class DataDictQueryVO extends BasePageVO {

    /**
     * 字典类型
     */
    @ApiModelProperty(value = "字典类型", name = "dictType")
    private String dictType;

    /**
     * 字典名称
     */
    @ApiModelProperty(value = "字典名称", name = "name")
    private String name;

    /**
     * 字典编码
     */
    @ApiModelProperty(value = "字典编码", name = "code")
    private String code;


}
