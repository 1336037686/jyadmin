package com.jyadmin.system.post.model.vo;

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
@ApiModel("岗位-查询-数据模型")
@Data
public class PostQueryVO extends BasePageVO implements Serializable {

    /**
     * 岗位名称
     */
    @ApiModelProperty("岗位名称")
    private String name;

    /**
     * 岗位编号
     */
    @ApiModelProperty("岗位编号")
    private String code;

    /**
     * 状态 0=禁用 1=启用
     */
    @ApiModelProperty("状态")
    private Integer status;

}
