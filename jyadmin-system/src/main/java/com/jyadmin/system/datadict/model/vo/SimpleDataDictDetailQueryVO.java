package com.jyadmin.system.datadict.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 通用数据字典详情
 * @TableName sys_simple_data_dict_detail
 */
@ApiModel("通用数据字典详情-查询-数据模型")
@Data
public class SimpleDataDictDetailQueryVO implements Serializable {

    /**
     * 字段名称
     */
    @ApiModelProperty(value = "字段名称", name = "name")
    private String name;

    /**
     * 字段编码
     */
    @ApiModelProperty(value = "字段编码", name = "code")
    private String code;

    /**
     * 通用字典ID
     */
    @ApiModelProperty(value = "通用字典ID", name = "dataDictId")
    @NotBlank(message = "通用字典ID不能为空")
    private String dataDictId;

}