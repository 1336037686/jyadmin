package com.jyadmin.system.permission.menu.model.vo;

import com.jyadmin.domain.BasePageVO;
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
@ApiModel("系统权限菜单-查询-数据模型")
@Data
public class PermissionMenuQueryVO extends BasePageVO implements Serializable {

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
     * 父类ID
     */
    @ApiModelProperty(value = "父类ID", name = "parentId")
    @NotBlank(message = "父类ID不能为空")
    private String parentId;

    /**
     * 路由地址
     */
    @ApiModelProperty(value = "路由地址", name = "url")
    @NotBlank(message = "路由地址不能为空")
    private String url;

    /**
     * 组件路径
     */
    @ApiModelProperty(value = "组件路径", name = "path")
    @NotBlank(message = "组件路径不能为空")
    private String path;

    /**
     * 是否缓存
     */
    @ApiModelProperty(value = "是否缓存", name = "cache")
    @NotNull(message = "是否缓存不能为空")
    private Integer cache;

    /**
     * 是否显示
     */
    @ApiModelProperty(value = "是否显示", name = "visiable")
    @NotNull(message = "是否显示不能为空")
    private Integer visiable;

    /**
     * 是否外链
     */
    @ApiModelProperty(value = "是否外链", name = "link")
    @NotNull(message = "是否外链不能为空")
    private Integer link;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", name = "status")
    @NotNull(message = "状态不能为空")
    private Integer status;

}
