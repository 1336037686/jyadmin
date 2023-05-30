package com.jyadmin.security.model;

import org.apache.commons.io.IOUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * 自定义request包装类，包装原始的 HttpServletRequest 对象
 * 避免获取参数后后面参数获取异常
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-05-30 16:55 <br>
 * @description: JsonBodyHttpServletRequestWrapper <br>
 */
public class JsonBodyHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] body;

    public JsonBodyHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        body = IOUtils.toByteArray(request.getInputStream());
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new DelegatingServletInputStream(new ByteArrayInputStream(body));
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    public String getBody() {
        return new String(this.body, StandardCharsets.UTF_8);
    }
}