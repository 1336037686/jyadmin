package com.jyadmin.util;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * 配置加密工具类
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-01-04 19:44 <br>
 * @description: JasyptEncryptorUtil <br>
 */
public final class JasyptEncryptorUtil {

    public static void main(String[] args) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //加密所需的salt(盐)
        textEncryptor.setPassword("jyadmin");
        //要加密的数据（数据库的用户名或密码）
        String username = textEncryptor.encrypt("root");
        String password = textEncryptor.encrypt("root");
        System.out.println("username:"+username);
        System.out.println("password:"+password);
    }

}