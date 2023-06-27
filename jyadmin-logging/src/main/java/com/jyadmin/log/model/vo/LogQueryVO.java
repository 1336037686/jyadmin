package com.jyadmin.log.model.vo;

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
@ApiModel("操作日志-查询-数据模型")
@Data
public class LogQueryVO extends BasePageVO implements Serializable {

    /**
     * 操作名称
     */
    @ApiModelProperty(value = "操作名称", name = "handleName")
    private String handleName;

    /**
     * 请求类型
     */
    @ApiModelProperty(value = "请求类型", name = "requestMethod")
    private String requestMethod;

    /**
     * 执行状态
     */
    @ApiModelProperty(value = "执行状态", name = "executeStatus")
    private Integer executeStatus;

    /**
     * 操作用户
     */
    @ApiModelProperty(value = "操作用户", name = "username")
    private String username;


}
