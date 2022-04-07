package com.jyblog.util;

import com.jyblog.domain.Result;

/**
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
