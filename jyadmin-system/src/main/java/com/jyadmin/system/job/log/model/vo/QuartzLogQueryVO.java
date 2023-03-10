package com.jyadmin.system.job.log.model.vo;

import com.jyadmin.domain.BasePageVO;
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
@ApiModel("系统定时任务日志-查询-数据模型")
@Data
public class QuartzLogQueryVO extends BasePageVO implements Serializable {

    /**
     * 任务ID
     */
    @ApiModelProperty(value = "任务ID", name = "jobId")
    private String jobId;

}
