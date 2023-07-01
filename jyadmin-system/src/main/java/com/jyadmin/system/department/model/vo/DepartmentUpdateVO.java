package com.jyadmin.system.department.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
@ApiModel("部门-修改-数据模型")
@Data
public class DepartmentUpdateVO implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty("ID")
    @NotNull(message = "ID不能为空")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

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
    @NotNull(message = "是否顶级部门不能为空")
    private Integer isRoot;

    /**
     * 上级部门ID
     */
    @ApiModelProperty("上级部门ID")
    @NotNull(message = "上级部门ID不能为空")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;

    /**
     * 状态 0=禁用 1=启用
     */
    @ApiModelProperty("状态")
    @NotNull(message = "状态不能为空")
    private Integer status;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    @NotNull(message = "排序不能为空")
    private Integer sort;

}
