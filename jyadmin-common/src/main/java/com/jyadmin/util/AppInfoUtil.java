package com.jyadmin.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * 启动信息打印工具类
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-06-26 20:34 <br>
 * @description: AppInfoUtil <br>
 */
@Slf4j
public class AppInfoUtil {

    private static final String SERVER_SSL_KEY_STORE = "server.ssl.key-store";
    private static final String API_DOC_PAGE = "doc.html";

    public static void printAppInfo(ConfigurableApplicationContext app) {
        Environment env = app.getEnvironment();

        // 获取应用程序名称、协议、主机地址、服务器端口号等信息
        String appName = env.getProperty("spring.application.name");
        String serverPort = env.getProperty("server.port");
        String protocol = Objects.isNull(env.getProperty(SERVER_SSL_KEY_STORE)) ? "http" : "https";
        String hostAddress = null;
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        String apiDocUrl = String.format("%s://%s:%s/%s", protocol, hostAddress, serverPort, API_DOC_PAGE);
        String localUrl = String.format("%s://localhost:%s", protocol, serverPort);
        String externalUrl = String.format("%s://%s:%s", protocol, hostAddress, serverPort);
        String appNameMsg = String.format("'%s' is running!", appName);


        // 定义矩形边框的左右两个字符
        final char leftBorderChar = '┃';
        final char rightBorderChar = '┃';

        // 定义矩形框的宽度和高度
        final int width = 80;

        // 填充矩形框内的文本内容
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("\t%s Application:    %-" + (width - 19) + "s%s", leftBorderChar, appNameMsg, rightBorderChar)).append("\n");
        sb.append(String.format("\t%s%s%s", leftBorderChar, "━".repeat(width - 2), rightBorderChar)).append("\n");
        sb.append(String.format("\t%s Access URLs:    %-" + (width - 19) + "s%s", leftBorderChar, "", rightBorderChar)).append("\n");
        sb.append(String.format("\t%s - Local:        %-" + (width - 19) + "s%s", leftBorderChar, localUrl, rightBorderChar)).append("\n");
        sb.append(String.format("\t%s - External:     %-" + (width - 19) + "s%s", leftBorderChar, externalUrl, rightBorderChar)).append("\n");
        sb.append(String.format("\t%s - API doc:      %-" + (width - 19) + "s%s", leftBorderChar, apiDocUrl, rightBorderChar)).append("\n");
        sb.append(String.format("\t%s - Profile(s):   %-" + (width - 19) + "s%s", leftBorderChar, String.join(",", env.getActiveProfiles()), rightBorderChar)).append("\n");

        // 绘制矩形框
        StringBuilder border = new StringBuilder();
        border.append("\t┏").append("━".repeat(width - 2)).append("┓").append("\n"); // 绘制顶部边框
        border.append(sb.toString()); // 填充文本内容
        border.append("\t┗").append("━".repeat(width - 2)).append("┛").append("\n"); // 绘制底部边框

        // 打印结果
        log.info(AnsiOutput.toString(AnsiColor.CYAN, "\n\n" + border.toString()));
    }

}
