package com.jyadmin.monitor.server.model.vo;

import cn.hutool.core.util.NumberUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-12-10 21:06 <br>
 * @description: CpuInfo <br>
 */
@ApiModel("服务器性能监控-CPU信息-数据模型")
@Data
@Accessors(chain = true)
public class CpuInfo {

    /**
     * CPU核心数
     */
    @ApiModelProperty("CPU核心数")
    private Integer cpuNum;

    /**
     * CPU总的使用率
     */
    @ApiModelProperty("CPU总的使用率")
    private double toTal;

    /**
     * CPU系统使用率
     */
    @ApiModelProperty("CPU系统使用率")
    private double sys;

    /**
     * CPU用户使用率
     */
    @ApiModelProperty("CPU用户使用率")
    private double user;

    /**
     * CPU当前等待率
     */
    @ApiModelProperty("CPU当前等待率")
    private double wait;

    /**
     * CPU当前空闲率
     */
    @ApiModelProperty("CPU当前空闲率")
    private double free;

    /**
     * CPU型号信息
     */
    @ApiModelProperty("CPU型号信息")
    private String cpuModel;

    /**
     * 获取用户+系统的总的CPU使用率
     *
     * @return 总CPU使用率
     */
    @ApiModelProperty("总CPU使用率")
    public double getUsed() {
        return NumberUtil.sub(100, this.free);
    }

}
