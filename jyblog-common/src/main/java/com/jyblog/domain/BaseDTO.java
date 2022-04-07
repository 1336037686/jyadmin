package com.jyblog.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-05 02:18 <br>
 * @description: BaseDTO <br>
 */
@Data
public class BaseDTO implements Serializable {

    private Date createTime;

    private Date updateTime;

    private String createBy;

    private String updateBy;
}
