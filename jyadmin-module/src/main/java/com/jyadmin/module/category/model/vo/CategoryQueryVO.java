package com.jyadmin.module.category.model.vo;

import com.jyadmin.domain.base.BasePageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-09 15:54 <br>
 * @description: TagCreateVO <br>
 */
@ApiModel("类别-查询-数据模型")
@Data
public class CategoryQueryVO extends BasePageVO implements Serializable {

    /**
     * 标签名称
     */
    @ApiModelProperty(value = "标签名称", name = "name")
    @NotBlank(message = "标签名称不能为空")
    private String name;

    /**
     * 标签编码
     */
    @ApiModelProperty(value = "标签编码", name = "code")
    @NotBlank(message = "标签编码不能为空")
    private String code;

}
