package com.jyblog.util;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import net.dreamlu.mica.ip2region.core.Ip2regionSearcher;
import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-30 16:02 <br>
 * @description: IPutil <br>
 */
@Slf4j
public class IpUtil {


    private static final String UNKNOWN = "unknown";

    private final static Ip2regionSearcher IP2REGIONSEARCHER = SpringUtil.getBean(Ip2regionSearcher.class);

    private static final UserAgentAnalyzer USER_AGENT_ANALYZER = UserAgentAnalyzer
            .newBuilder().hideMatcherLoadStats().withCache(10000)
            .withField(UserAgent.AGENT_NAME_VERSION).build();

    /**
     * 获取ip地址
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String comma = ",";
        String localhost = "127.0.0.1";
        if (ip.contains(comma)) {
            ip = ip.split(",")[0];
        }
        if (localhost.equals(ip)) {
            // 获取本机真正的ip地址
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return ip;
    }

    /**
     * 根据ip获取详细地址
     */
    public static String getAddress(String ip) {
        return IP2REGIONSEARCHER.getAddress(ip);
    }

    /**
     * 获取地址信息包含 isp
     * @param ip
     * @return
     */
    public static String getAddressAndIsp(String ip) {
        return IP2REGIONSEARCHER.getAddressAndIsp(ip);
    }

    /**
     * 获取浏览器
     * @param request
     * @return
     */
    public static String getBrowser(HttpServletRequest request) {
        UserAgent.ImmutableUserAgent userAgent = USER_AGENT_ANALYZER.parse(request.getHeader("User-Agent"));
        return userAgent.get(UserAgent.AGENT_NAME_VERSION).getValue();
    }

    /**
     * 获取设备类型
     * @param request
     * @return
     */
    public static String getMachine(HttpServletRequest request) {
        UserAgent.ImmutableUserAgent userAgent = USER_AGENT_ANALYZER.parse(request.getHeader("User-Agent"));
        return userAgent.get(UserAgent.DEVICE_CLASS).getValue();
    }
}
