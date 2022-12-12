package com.jyadmin.monitor.server.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-12-08 23:45 <br>
 * @description: MemoryInfo <br>
 */
@ApiModel("服务器性能监控-内存信息-数据模型")
@Data
@Accessors(chain = true)
public class MemoryInfo implements Serializable {

    /**
     * 内存总量
     */
    @ApiModelProperty("内存总量（bytes）")
    private double total;

    /**
     * 已用内存
     */
    @ApiModelProperty("已用内存（bytes）")
    private double used;

    /**
     * 剩余内存
     */
    @ApiModelProperty("剩余内存（bytes）")
    private double free;

}
