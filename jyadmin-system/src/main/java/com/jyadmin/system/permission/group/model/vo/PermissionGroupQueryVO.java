package com.jyadmin.system.permission.group.model.vo;

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
@ApiModel("系统权限组别-查询-数据模型")
@Data
public class PermissionGroupQueryVO extends BasePageVO implements Serializable {

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
     * 父类ID
     */
    @ApiModelProperty(value = "父类ID", name = "parentId")
    @NotBlank(message = "父类ID不能为空")
    private String parentId;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", name = "status")
    @NotNull(message = "状态不能为空")
    private Integer status;

}
