package com.jyadmin.monitor.server.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-12-10 20:47 <br>
 * @description: SystemInfo <br>
 */
@ApiModel("服务器性能监控-操作系统信息-数据模型")
@Data
@Accessors(chain = true)
public class SystemInfo {

    /**
     * 服务器名称
     */
    @ApiModelProperty("服务器名称")
    private String computerName;

    /**
     * 服务器Ip
     */
    @ApiModelProperty("服务器Ip")
    private String computerIp;

    /**
     * 项目路径
     */
    @ApiModelProperty("项目路径")
    private String userDir;

    /**
     * 操作系统
     */
    @ApiModelProperty("操作系统")
    private String osName;

    /**
     * 系统架构
     */
    @ApiModelProperty("系统架构")
    private String osArch;

}
