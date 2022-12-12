package com.jyadmin.monitor.server.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-12-10 20:49 <br>
 * @description: NetworkInfo <br>
 */
@ApiModel("服务器性能监控-网络信息-数据模型")
@Data
@Accessors(chain = true)
public class NetworkInfo {

    @ApiModelProperty("显示名称")
    private String displayName;

    @ApiModelProperty("MAC地址")
    private String macAddr;

    @ApiModelProperty("ipv4地址")
    private String ipv4Addr;

    @ApiModelProperty("ipv6地址")
    private String ipv6Addr;

    @ApiModelProperty("速率（bits/s）")
    private Long speed;



}
