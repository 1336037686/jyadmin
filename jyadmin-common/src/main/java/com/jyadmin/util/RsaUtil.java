package com.jyadmin.util;

import cn.hutool.extra.spring.SpringUtil;
import com.jyadmin.config.properties.JyRsaProperties;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import javax.crypto.Cipher;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

/**
 * RSA加解密
 * openssl genrsa -out private_key.pem 2048 # 生成私钥
 * openssl rsa -in private_key.pem -pubout -out public_key.pem # 生成公钥
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-06-21 15:52 <br>
 * @description: RSAUtil <br>
 */
public class RsaUtil {


    /**
     * RSA解密
     * @param encryptedData 密文
     * @return /
     */
    public static String decrypt(String encryptedData) {
        JyRsaProperties rsaProperties = SpringUtil.getBean(JyRsaProperties.class);
        if (StringUtils.isBlank(rsaProperties.getPrivateKey())) throw new RuntimeException("PrivateKey not exist");
        try {
            PEMParser pemParser = new PEMParser(new StringReader(rsaProperties.getPrivateKey()));
            Object object = pemParser.readObject();
            BouncyCastleProvider provider = new BouncyCastleProvider();
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider(provider);
            PrivateKey privateKey = converter.getPrivateKey(((PEMKeyPair) object).getPrivateKeyInfo());
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", provider);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * RSA加密
     * @param data 待加密数据
     * @return /
     */
    public static String encrypt(String data) {
        JyRsaProperties rsaProperties = SpringUtil.getBean(JyRsaProperties.class);
        if (StringUtils.isBlank(rsaProperties.getPublicKey())) throw new RuntimeException("PublicKey not exist");
        try {
            PEMParser pemParser = new PEMParser(new StringReader(rsaProperties.getPrivateKey()));
            Object object = pemParser.readObject();
            BouncyCastleProvider provider = new BouncyCastleProvider();
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider(provider);
            PublicKey publicKey = converter.getPublicKey(((PEMKeyPair) object).getPublicKeyInfo());
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", provider);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

    }


}
