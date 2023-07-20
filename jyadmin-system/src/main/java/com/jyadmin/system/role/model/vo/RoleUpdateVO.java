package com.jyadmin.system.role.model.vo;

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
@ApiModel("系统角色-修改-数据模型")
@Data
public class RoleUpdateVO implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", name = "id")
    @NotNull(message = "ID不能为空")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称", name = "name")
    @NotBlank(message = "角色名称不能为空")
    private String name;

    /**
     * 角色编码
     */
    @ApiModelProperty(value = "角色编码", name = "code")
    @NotBlank(message = "角色编码不能为空")
    private String code;

    /**
     * 角色接口权限（api_permission_portion=根据角色菜单限制接口权限， api_permission_all=拥有全部接口权限）
     */
    @ApiModelProperty(value = "角色接口权限", name = "apiPermission")
    @NotBlank(message = "角色接口权限不能为空")
    private String apiPermission;

    /**
     * 数据范围（all=全部，local=本级，other=自定义）
     */
    @ApiModelProperty(value = "数据范围", name = "dataScope")
    @NotBlank(message = "数据范围不能为空")
    private String dataScope;

    /**
     * 自定义数据范围（ID集合，数据之间用,分隔）
     */
    @ApiModelProperty(value = "自定义数据范围", name = "userDefineDataScope")
    private String userDefineDataScope;

    /**
     * 角色描述
     */
    @ApiModelProperty(value = "角色描述", name = "description")
    @NotBlank(message = "角色描述不能为空")
    private String description;

    /**
     * 角色排序
     */
    @ApiModelProperty(value = "角色排序", name = "sort")
    @NotNull(message = "角色排序不能为空")
    private Integer sort;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", name = "status")
    @NotNull(message = "角色状态不能为空")
    private Integer status;

}
