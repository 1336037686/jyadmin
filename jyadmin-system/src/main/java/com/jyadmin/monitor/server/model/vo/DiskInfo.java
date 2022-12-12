package com.jyadmin.monitor.server.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-12-12 23:48 <br>
 * @description: DiskInfo <br>
 */
@ApiModel("服务器性能监控-磁盘信息-数据模型")
@Data
@Accessors(chain = true)
public class DiskInfo implements Serializable {

    /**
     * 盘符路径
     */
    @ApiModelProperty("盘符路径")
    private String dirName;

    /**
     * 盘符类型
     */
    @ApiModelProperty("盘符类型")
    private String sysTypeName;

    /**
     * 文件类型
     */
    @ApiModelProperty("文件类型")
    private String typeName;

    /**
     * 总大小
     */
    @ApiModelProperty("总大小")
    private Long total;

    /**
     * 剩余大小
     */
    @ApiModelProperty("剩余大小")
    private Long free;

    /**
     * 已经使用量
     */
    @ApiModelProperty("已经使用量")
    private Long used;

    /**
     * 资源的使用率
     */
    @ApiModelProperty("资源的使用率")
    private Double usage;


}
