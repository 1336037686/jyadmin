package com.jyadmin.security.model;

import org.springframework.util.Assert;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Servlet输入流获取实现类
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-05-30 16:56 <br>
 * @description: DelegatingServletInputStream <br>
 */
public class DelegatingServletInputStream extends ServletInputStream {
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