package com.jyadmin.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常工具类
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-07 00:06 <br>
 * @description: ThrowableUtil <br>
 */
public class ThrowableUtil {

    /**
     * 获取堆栈信息
     */
    public static String getStackTrace(Throwable throwable){
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }

}
