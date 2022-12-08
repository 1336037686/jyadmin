package com.jyadmin.monitor.server.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-12-08 23:45 <br>
 * @description: MemoryInfo <br>
 */
@Data
@Accessors(chain = true)
public class MemoryInfo implements Serializable {

    /**
     * 内存总量
     */
    private double total;

    /**
     * 已用内存
     */
    private double used;

    /**
     * 剩余内存
     */
    private double free;

}
