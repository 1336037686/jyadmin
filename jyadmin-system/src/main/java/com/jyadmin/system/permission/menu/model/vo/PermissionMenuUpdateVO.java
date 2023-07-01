package com.jyadmin.system.permission.menu.model.vo;

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
@ApiModel("系统权限菜单-修改-数据模型")
@Data
public class PermissionMenuUpdateVO implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", name = "id")
    @NotNull(message = "ID不能为空")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称", name = "name")
    @NotBlank(message = "菜单名称不能为空")
    private String name;

    /**
     * 菜单标识
     */
    @ApiModelProperty(value = "菜单标识", name = "code")
    @NotBlank(message = "菜单标识不能为空")
    private String code;

    /**
     * 菜单类别
     */
    @ApiModelProperty(value = "菜单类别", name = "type")
    @NotNull(message = "菜单类别不能为空")
    private Integer type;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述", name = "description")
    private String description;

    /**
     * 父类ID
     */
    @ApiModelProperty(value = "父类ID", name = "parentId")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;

    /**
     * 路由地址
     */
    @ApiModelProperty(value = "路由地址", name = "url")
    private String url;

    /**
     * 组件路径
     */
    @ApiModelProperty(value = "组件路径", name = "path")
    private String path;

    /**
     * 组件名称
     */
    @ApiModelProperty(value = "组件名称", name = "component")
    private String component;

    /**
     * 调用方法
     */
    @ApiModelProperty(value = "调用方法", name = "method")
    private String method;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标", name = "icon")
    private String icon;

    /**
     * 样式
     */
    @ApiModelProperty(value = "样式", name = "icon")
    private String style;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", name = "sort")
    @NotNull(message = "排序不能为空")
    private Integer sort;

    /**
     * 是否缓存
     */
    @ApiModelProperty(value = "是否缓存", name = "cache")
    private Integer cache;

    /**
     * 是否显示
     */
    @ApiModelProperty(value = "是否显示", name = "visiable")
    private Integer visiable;

    /**
     * 是否外链
     */
    @ApiModelProperty(value = "是否外链", name = "link")
    private Integer link;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", name = "status")
    @NotNull(message = "状态不能为空")
    private Integer status;

}
