package com.jyadmin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 其他配置
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-29 21:35 <br>
 * @description: JyOtherConfig <br>
 */
// 引入hutool SpringUtil工具类
@Import(cn.hutool.extra.spring.SpringUtil.class)
@Configuration
public class OtherConfig {

}
