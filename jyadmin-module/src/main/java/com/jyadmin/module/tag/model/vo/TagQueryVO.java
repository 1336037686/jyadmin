package com.jyadmin.module.tag.model.vo;

import com.jyadmin.domain.base.BasePageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-09 15:54 <br>
 * @description: TagCreateVO <br>
 */
@ApiModel("标签-查询-数据模型")
@Data
public class TagQueryVO extends BasePageVO implements Serializable {

    /**
     * 标签名称
     */
    @ApiModelProperty(value = "标签名称", name = "name")
    private String name;

    /**
     * 标签编码
     */
    @ApiModelProperty(value = "标签编码", name = "code")
    private String code;

}
