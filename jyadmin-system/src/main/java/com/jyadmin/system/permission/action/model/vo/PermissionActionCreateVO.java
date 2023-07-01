package com.jyadmin.system.permission.action.model.vo;

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
@ApiModel("系统权限动作-新增-数据模型")
@Data
public class PermissionActionCreateVO implements Serializable {

    /**
     * 动作名称
     */
    @ApiModelProperty(value = "动作名称", name = "name")
    @NotBlank(message = "动作名称不能为空")
    private String name;

    /**
     * 权限标识
     */
    @ApiModelProperty(value = "权限标识", name = "code")
    @NotBlank(message = "权限标识不能为空")
    private String code;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述", name = "description")
    @NotBlank(message = "描述不能为空")
    private String description;

    /**
     * 所属权限组
     */
    @ApiModelProperty(value = "所属权限组", name = "groupId")
    @NotNull(message = "所属权限组不能为空")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long groupId;

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
    @NotNull(message = "状态不能为空")
    private Integer status;

}
