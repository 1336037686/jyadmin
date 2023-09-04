package com.jyadmin.util;

import cn.hutool.core.lang.Snowflake;

/**
 * 随机ID生成
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-09-03 11:51 <br>
 * @description: RandomIdUtil <br>
 */
public class RandomIdUtil {

    private static Snowflake SNOWFLAKE = new Snowflake();


    public static String nextIdStr() {
        return SNOWFLAKE.nextIdStr();
    }

}
