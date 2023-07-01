package com.jyadmin.module.tag.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
@ApiModel("标签-修改-数据模型")
@Data
public class TagUpdateVO implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value = "标签ID", name = "id")
    @NotBlank(message = "标签ID不能为空")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

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

    /**
     * 简介
     */
    @ApiModelProperty(value = "标签简介", name = "intro")
    @NotBlank(message = "标签简介不能为空")
    private String intro;

}
