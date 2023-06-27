package com.jyadmin.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * RSA加密
 * openssl genrsa -out private_key.pem 2048 # 生成私钥
 * openssl rsa -in private_key.pem -pubout -out public_key.pem # 生成公钥
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-06-21 18:00 <br>
 * @description: JyRsaProperties <br>
 */
@ConfigurationProperties(prefix = JyConfigItemConstant.RSA)
@Component
@Data
public class JyRsaProperties {

    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 私钥
     */
    private String privateKey;

}
