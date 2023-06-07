package com.jyadmin.util;

import com.jyadmin.domain.Result;

/**
 * 结果返回工具类
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-07 22:33 <br>
 * @description: ResultUtil <br>
 */
public class ResultUtil {

    public static Result<Object> toResult(boolean result) {
        return result ? Result.ok() : Result.fail();
    }

}
