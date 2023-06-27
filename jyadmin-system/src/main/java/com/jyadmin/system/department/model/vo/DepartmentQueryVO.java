package com.jyadmin.system.department.model.vo;

import com.jyadmin.domain.base.BasePageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-09 15:54 <br>
 * @description: TagCreateVO <br>
 */
@ApiModel("部门-查询-数据模型")
@Data
public class DepartmentQueryVO extends BasePageVO implements Serializable {

    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    private String name;

    /**
     * 部门编号
     */
    @ApiModelProperty("部门编号")
    private String code;

    /**
     * 是否顶级部门（0=非顶级部门，1=顶级部门）
     */
    @ApiModelProperty("是否顶级部门")
    private Integer isRoot;

    /**
     * 状态 0=禁用 1=启用
     */
    @ApiModelProperty("状态")
    private Integer status;
}
