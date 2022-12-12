package com.jyadmin.monitor.server.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-12-10 20:44 <br>
 * @description: JvmInfo <br>
 */
@ApiModel("服务器性能监控-JVM信息-数据模型")
@Data
@Accessors(chain = true)
public class JvmInfo {

    /**
     * JVM impl.的名称（取自系统属性：java.vm.name）
     */
    @ApiModelProperty(value = "JVM名称", name = "name")
    private String name;

    /**
     * 当前JVM impl.的版本（取自系统属性：java.vm.version）
     */
    @ApiModelProperty(value = "JVM版本", name = "version")
    private String version;

    /**
     * 当前JVM impl.的厂商（取自系统属性：java.vm.vendor）
     */
    @ApiModelProperty(value = "JVM厂商", name = "vendor")
    private String vendor;

    // ============== 运行时信息，包括内存总大小、已用大小、可用大小等 =============

    /**
     * 获得JVM已分配内存中的剩余空间
     */
    @ApiModelProperty(value = "JVM已分配内存中的剩余空间（bytes）", name = "freeMemory")
    private Long freeMemory;

    /**
     * 获得JVM最大内存
     */
    @ApiModelProperty(value = "JVM最大内存（bytes）", name = "maxMemory")
    private Long maxMemory;

    /**
     * 获得JVM已分配内存
     */
    @ApiModelProperty(value = "JVM已分配内存（bytes）", name = "totalMemory")
    private Long totalMemory;

    /**
     * 获得JVM最大可用内存
     */
    @ApiModelProperty(value = "JVM最大可用内存（bytes）", name = "usableMemory")
    private Long usableMemory;

    /**
     * JDK路径
     */
    @ApiModelProperty(value = "JDK路径", name = "home")
    private String home;
}
