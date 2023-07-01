package com.jyadmin.system.permission.group.model.vo;

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
@ApiModel("系统权限组别-修改-数据模型")
@Data
public class PermissionGroupUpdateVO implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", name = "id")
    @NotBlank(message = "ID不能为空")
    private String id;

    /**
     * 权限组别名称
     */
    @ApiModelProperty(value = "权限组别名称", name = "name")
    @NotBlank(message = "权限组别名称不能为空")
    private String name;

    /**
     * 权限组别标识
     */
    @ApiModelProperty(value = "权限组别标识", name = "code")
    @NotBlank(message = "权限组别标识不能为空")
    private String code;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述", name = "description")
    private String description;

    /**
     * 父类ID
     */
    @ApiModelProperty(value = "父类ID", name = "parentId")
    @NotNull(message = "父类ID不能为空")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", name = "sort")
    @NotNull(message = "排序不能为空")
    private Integer sort;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", name = "status")
    private Integer status;

}
