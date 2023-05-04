package com.jyadmin.system.department.model.vo;

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
@ApiModel("部门-新增-数据模型")
@Data
public class DepartmentCreateVO implements Serializable {

    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    @NotBlank(message = "部门名称不能为空")
    private String name;

    /**
     * 部门编号
     */
    @ApiModelProperty("部门编号")
    @NotBlank(message = "部门编号不能为空")
    private String code;

    /**
     * 是否顶级部门（0=非顶级部门，1=顶级部门）
     */
    @ApiModelProperty("是否顶级部门")
    @NotBlank(message = "是否顶级部门不能为空")
    private Integer isRoot;

    /**
     * 上级部门ID
     */
    @ApiModelProperty("上级部门ID")
    @NotBlank(message = "上级部门ID不能为空")
    private String parentId;

    /**
     * 状态 0=禁用 1=启用
     */
    @ApiModelProperty("状态")
    @NotBlank(message = "状态不能为空")
    private Integer status;

}
