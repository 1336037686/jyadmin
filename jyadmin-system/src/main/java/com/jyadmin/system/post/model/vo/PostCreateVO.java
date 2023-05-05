package com.jyadmin.system.post.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-09 15:54 <br>
 * @description: TagCreateVO <br>
 */
@ApiModel("岗位-新增-数据模型")
@Data
public class PostCreateVO implements Serializable {

    /**
     * 岗位名称
     */
    @ApiModelProperty("岗位名称")
    @NotBlank(message = "岗位名称不能为空")
    private String name;

    /**
     * 岗位编号
     */
    @ApiModelProperty("岗位编号")
    @NotBlank(message = "岗位编号不能为空")
    private String code;

    /**
     * 岗位简介
     */
    @ApiModelProperty("岗位简介")
    private String description;

    /**
     * 状态 0=禁用 1=启用
     */
    @ApiModelProperty("状态")
    @NotNull(message = "状态不能为空")
    private Integer status;

}
