package com.jyadmin.domain.base;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 公共DTO数据传输对象公共实体类
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-05 02:18 <br>
 * @description: BaseDTO <br>
 */
@Data
public class BaseDTO implements Serializable {

    private static final long serialVersionUID = 1;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    private Integer deleted;
}
