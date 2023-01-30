package com.jyadmin.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Random;

/**
 * 验证码字符工具类
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-01-30 17:17 <br>
 * @description: VerifyCodeUtil <br>
 */
public class VerifyCodeUtil {
    public static final String VERIFY_CODES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    public static final String NUMBER_VERIFY_CODES = "0123456789";

    private static Random random = new Random();

    /**
     * 使用系统默认字符源生成验证码
     * @param verifySize 验证码长度
     * @return String
     */
    public static String generateVerifyCode(int verifySize) {
        return generateVerifyCode(verifySize, VERIFY_CODES);
    }

    /**
     * 使用系统默认字符源生成数字验证码
     * @param verifySize 验证码长度
     * @return String
     */
    public static String generateNumberVerifyCode(int verifySize) {
        return generateVerifyCode(verifySize, NUMBER_VERIFY_CODES);
    }

    /**
     * 使用指定源生成验证码
     * @param verifySize 验证码长度
     * @param sources 验证码字符源
     * @return String
     */
    public static String generateVerifyCode(int verifySize, String sources) {
        if (StringUtils.isEmpty(sources)) sources = VERIFY_CODES;
        int codesLen = sources.length();
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder verifyCode = new StringBuilder(verifySize);
        for (int i = 0; i < verifySize; i++) verifyCode.append(sources.charAt(rand.nextInt(codesLen - 1)));
        return verifyCode.toString();
    }


}
