package com.jyadmin.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jyadmin.domain.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Response 工具类
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-07 21:12 <br>
 * @description: PageUtil <br>
 */
public class ResponseUtil {

    /**
     * 初始化文件传输响应对象，标识当前响应类型为文件传输
     * 初始化文件传输的Response对象，设置一些初始值
     * @param response /
     */
    public static void initFtpResponse(HttpServletResponse response) {
        // 自定义的header， requestType = file，标志该响应是文件传输
        response.setHeader("requestType","file");
        // 设置这个header 可见
        response.setHeader("Access-Control-Expose-Headers", "requestType");
    }

    /**
     * 初始化Xlsx文件传输响应对象，设置一些初始值
     * @param response /
     */
    public static void initXlsxResponse(HttpServletResponse response, String fileName) {
        // 设置当前响应为 文件传输
        ResponseUtil.initFtpResponse(response);
        // 设置文件传输对象为Excel的一些基础设置
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        try {
            fileName= URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+","%20");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        response.setHeader("Content-disposition","attachment;filename="+fileName+".xlsx");
    }

    /**
     * 输出Result
     * @param response /
     * @param result /
     */
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
