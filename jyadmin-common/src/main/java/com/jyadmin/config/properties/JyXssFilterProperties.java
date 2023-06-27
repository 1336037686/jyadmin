package com.jyadmin.config.properties;

import cn.hutool.core.util.ArrayUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * XSS过滤器配置
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-07-04 22:57 <br>
 * @description: JyXssFilterProperties <br>
 */
@ConfigurationProperties(prefix = JyConfigItemConstant.SAFE_XSS)
@Component
@Data
public class JyXssFilterProperties {

    /**
     * 放行所有接口
     */
    private Boolean permitAll = false;

    /**
     * 免XSS过滤白名单地址配置
     */
    private List<String> ignoreUrls = new ArrayList<>();

    /**
     * 获取白名单URL
     * @return /
     */
    public String[] getIgnoreUrls() {
        String[] configIgnoreUrls = this.ignoreUrls.stream().toArray(String[]::new);
        String[] commonIgnoreUrls = getCommonIgnoreUrls();
        return ArrayUtil.addAll(configIgnoreUrls, commonIgnoreUrls);
    }

    /**
     * 公共白名单地址
     * @return /
     */
    public String[] getCommonIgnoreUrls() {
        return new String[] {};
    }

}
