package com.jyadmin.monitor.runtimeLog.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-06-04 09:56 <br>
 * @description: RuntimeLogDirReqVO <br>
 */
@ApiModel("运行时日志-获取日志内容-数据模型")
@Data
@Accessors(chain = true)
public class RuntimeLogReqVO {

    @ApiModelProperty(value = "文件名称", name = "name")
    @NotBlank(message = "文件名称不能为空")
    private String name;

    @ApiModelProperty(value = "文件路径", name = "path")
    @NotBlank(message = "文文件路径不能为空")
    private String path;

    @ApiModelProperty(value = "文件类型", name = "type")
    @NotBlank(message = "文件类型不能为空")
    private String type;

}
