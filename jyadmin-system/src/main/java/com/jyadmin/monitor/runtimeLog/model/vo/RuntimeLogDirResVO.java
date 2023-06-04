package com.jyadmin.monitor.runtimeLog.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-06-04 09:56 <br>
 * @description: RuntimeLogDirReqVO <br>
 */
@ApiModel("运行时日志-响应-目录-数据模型")
@Data
@Accessors(chain = true)
public class RuntimeLogDirResVO {

    @ApiModelProperty(value = "id", name = "id")
    private String id;

    @ApiModelProperty(value = "文件名称", name = "name")
    private String name;

    @ApiModelProperty(value = "文件路径", name = "path")
    private String path;

    @ApiModelProperty(value = "文件类型", name = "type")
    private String type;

    @ApiModelProperty(value = "子文件", name = "children")
    private List<RuntimeLogDirResVO> children = new ArrayList<>();

}
