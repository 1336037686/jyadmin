package com.jyadmin.security.filter;

import com.google.common.html.HtmlEscapers;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.binary.XSSFBUtils;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Xss校验
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-05-29 22:34 <br>
 * @description: XssVerifyFilter <br>
 */
@Slf4j
public class XssVerifyFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 判断当前请求是不是application/json请求, 如果不是，直接放行
        String contentType = request.getContentType();
        if (StringUtils.isBlank(contentType) || !contentType.contains("application/json")) {
            filterChain.doFilter(request, response);
            return;
        }
        // 进行参数校验
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            JsonBodyHttpServletRequestWrapper requestWrapper = new JsonBodyHttpServletRequestWrapper(httpRequest);
            // 获取参数
            String requestBody = requestWrapper.getBody();
            // 校验参数
            log.info("XssVerifyFilter ----> " + requestBody);
            // 处理请求体数据
            filterChain.doFilter(requestWrapper, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    /**
     * 自定义request包装类，包装原始的 HttpServletRequest 对象
     * 避免获取参数后后面参数获取异常
     */
    public static class JsonBodyHttpServletRequestWrapper extends HttpServletRequestWrapper {

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

    /**
     * Servlet输入流获取实现类
     */
    public static class DelegatingServletInputStream extends ServletInputStream {
        private final InputStream sourceStream;
        private boolean finished = false;

        /**
         * 为给定的源流创建DelegatingServletInputStream。
         * @param sourceStream the source stream (never {@code null})
         */
        public DelegatingServletInputStream(InputStream sourceStream) {
            Assert.notNull(sourceStream, "Source InputStream must not be null");
            this.sourceStream = sourceStream;
        }

        /**
         * 返回底层源流 (never {@code null}).
         */
        public final InputStream getSourceStream() {
            return this.sourceStream;
        }

        @Override
        public int read() throws IOException {
            int data = this.sourceStream.read();
            if (data == -1) {
                this.finished = true;
            }
            return data;
        }

        @Override
        public int available() throws IOException {
            return this.sourceStream.available();
        }

        @Override
        public void close() throws IOException {
            super.close();
            this.sourceStream.close();
        }

        @Override
        public boolean isFinished() {
            return this.finished;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            throw new UnsupportedOperationException();
        }

    }

}
