package com.jyadmin.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jyadmin.domain.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-07 21:12 <br>
 * @description: PageUtil <br>
 */
public class ResponseUtil {

    /**
     * 初始化文件传输的Response对象，设置一些初始值
     * @param response /
     */
    public static void initFtpResponse(HttpServletResponse response) {
        // 自定义的header， requestType = file，标志该响应是文件传输
        response.setHeader("requestType","file");
        // 设置这个header 可见
        response.setHeader("Access-Control-Expose-Headers", "requestType");
    }

    public static void out(HttpServletResponse response, Result result) {
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            mapper.writeValue(response.getWriter(), result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
