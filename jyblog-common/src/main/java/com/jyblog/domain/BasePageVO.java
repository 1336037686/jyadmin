package com.jyblog.domain;

import java.io.Serializable;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-07 20:44 <br>
 * @description: BasePageVO <br>
 */
public class BasePageVO implements Serializable {

    private static final long serialVersionUID = 97792549823353464L;

    /**
     * 页码
     */
    private int pageNumber = 1;

    /**
     * 每页结果数
     */
    private int pageSize = 10;

}
