package com.jyadmin.util;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import javax.crypto.Cipher;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.SecretKeyFactory;
import java.security.Key;
import java.security.Security;
import java.util.Arrays;


/**
 * 加密工具类
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-06-04 00:40 <br>
 * @description: EncrypUtil <br>
 */
public class EncrypUtil {

    private static final String DEFAULT_KEY_HAX = "jyadminkeyhax123";

    /**
     * des 解密
     * @param message 数据
     * @return /
     */
    public static String decrypt(String message) {
        try {
            Security.addProvider(new BouncyCastleProvider());                       // 注册BouncyCastleProvider

            byte[] keyBytes = DEFAULT_KEY_HAX.getBytes();                           // 密钥字节数组
            byte[] ivBytes = Arrays.copyOf(keyBytes, 8);                 // 初始化向量字节数组

            DESKeySpec desKeySpec = new DESKeySpec(keyBytes);                       // 创建DESKeySpec对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");      // 创建SecretKeyFactory对象
            Key key = keyFactory.generateSecret(desKeySpec);                        // 生成SecretKey对象
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS7Padding", "BC"); // 创建Cipher对象
            IvParameterSpec iv = new IvParameterSpec(ivBytes);                      // 创建IvParameterSpec对象
            cipher.init(Cipher.DECRYPT_MODE, key, iv);                              // 初始化Cipher对象

            byte[] encryptedTextBytes = Base64.decodeBase64(message);               // 解码待解密的消息
            byte[] decryptedTextBytes = cipher.doFinal(encryptedTextBytes);         // 解密消息得到明文字节数组
            return new String(decryptedTextBytes);                                  // 返回解密后的明文字符串
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        System.out.println(EncrypUtil.decrypt("ZgER47Rgi2rS3cs0Itx4zw=="));
    }


}
