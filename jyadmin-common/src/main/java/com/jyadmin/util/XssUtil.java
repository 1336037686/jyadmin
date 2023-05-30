package com.jyadmin.util;

import java.util.regex.Pattern;

/**
 * Xss校验
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-05-30 17:23 <br>
 * @description: XssUtil <br>
 */
public class XssUtil {

    private static final String[] SENSITIVE_WORDS = {
            "<script>", "</script>",
            "<iframe>", "</iframe>",
            "<img>", "</img>",
            "<embed>", "</embed>",
            "<object>", "</object>",
            "<meta>", "</meta>",
            "<link>", "</link>",
            "<style>", "</style>",
            "javascript:", "onload=",
            "alert(", "eval(", "document.cookie",
            "document.write"
    };
    private static final Pattern PATTERN;

    static {
        StringBuilder sb = new StringBuilder();
        for (String word : SENSITIVE_WORDS) {
            sb.append("\\b").append(Pattern.quote(word)).append("\\b|"); // 使用\b单词边界确保匹配精度
        }
        sb.deleteCharAt(sb.length() - 1); // 删除最后一个|
        PATTERN = Pattern.compile(sb.toString(), Pattern.CASE_INSENSITIVE);
    }

    public static boolean containsSensitiveWords(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        return PATTERN.matcher(input).find();
    }

}
