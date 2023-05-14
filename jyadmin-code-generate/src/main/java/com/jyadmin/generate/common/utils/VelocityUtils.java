package com.jyadmin.generate.common.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.engine.velocity.VelocityEngine;

/**
 * Velocity 工具类
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-05-13 18:59 <br>
 * @description: VelocityUtils <br>
 */
public class VelocityUtils {

    /**
     * 创建模板引擎
     * @return /
     */
    public static VelocityEngine createVelocityEngine() {
        TemplateConfig config = new TemplateConfig();
        config.setCharset(CharsetUtil.CHARSET_UTF_8);
        config.setResourceMode(TemplateConfig.ResourceMode.CLASSPATH);
        return new VelocityEngine(config);
    }

}
