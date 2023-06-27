package com.jyadmin.system.job.manage.model.vo;

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
@ApiModel("系统定时任务-查询-数据模型")
@Data
public class QuartzJobQueryVO extends BasePageVO implements Serializable {

    /**
     * 任务编号
     */
    @ApiModelProperty(value = "任务编号", name = "code")
    private String code;

    /**
     * 任务名称
     */
    @ApiModelProperty(value = "任务名称", name = "name")
    private String name;

    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人", name = "principal")
    private String principal;

}
