package com.jyblog.consts;

/**
 * 返回状态码
 * 000 000
 * 前三位，表示具体模块
 * 后三位，表示具体状态
 *
 * 通用成功：000 200
 * 通用失败：000 400
 *
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-05 16:12 <br>
 * @description: JyResultStatus <br>
 */
public enum JyResultStatus {

    // --- 0000 xxxx 基础状态码 ---

    /**
     * 0000 0200 成功
     */
    SUCCESS(200, ModuleSeries.BASE, "操作成功"),

    /**
     * 000 0400 失败
     */
    FAIL(400, ModuleSeries.BASE, "操作失败"),

    // --- 0001 xxxx 权限相关状态码 ---

    /**
     * 0001 0001 权限不足
     */
    INSUFFICIENT_PERMISSIONS(10001, ModuleSeries.AUTH, "权限不足"),

    /**
     * 0001 0002 请求未授权
     */
    REQUEST_NOT_AUTHORIZED(10002, ModuleSeries.AUTH, "请求未授权"),

    /**
     * 0001 0003 账号被锁定
     */
    ACCOUNT_LOCKOUT(10003, ModuleSeries.AUTH, "账号被锁定，请联系管理员"),

    /**
     * 0001 0004 密码过期
     */
    PASSWORD_EXPIRATION(10004, ModuleSeries.AUTH, "密码过期，请联系管理员"),

    /**
     * 0001 0005 账户过期
     */
    ACCOUNT_EXPIRATION(10005, ModuleSeries.AUTH, "账户过期，请联系管理员"),

    /**
     * 0001 0006 账户被禁用
     */
    ACCOUNT_DISABLED(10006, ModuleSeries.AUTH, "账户被禁用，请联系管理员"),

    /**
     * 0001 0007 用户名或者密码输入错误
     */
    USERNAME_PASSWORD_ERROR(10007, ModuleSeries.AUTH, "用户名或者密码输入错误，请重新输入"),

    /**
     * 0001 0008 当前登录状态过期
     */
    LOGIN_STATUS_EXPIRED(10008, ModuleSeries.AUTH, "当前登录状态过期"),

    /**
     * 0001 0009 找不到当前登录的信息
     */
    NOT_FOUND_LOGIN_INFO(10009, ModuleSeries.AUTH, "找不到当前登录的信息")

    ;

    // 状态码
    private final int value;

    // 模块类别
    private final ModuleSeries moduleSeries;

    // 提示
    private final String reasonPhrase;

    JyResultStatus(int value, ModuleSeries moduleSeries, String reasonPhrase) {
        this.value = value;
        this.moduleSeries = moduleSeries;
        this.reasonPhrase = reasonPhrase;
    }

    public int getValue() {
        return value;
    }

    public ModuleSeries getModuleSeries() {
        return moduleSeries;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    /**
     * 模块类别枚举
     */
    public enum ModuleSeries {

        // 0 基础
        BASE(0),
        // 1 权限
        AUTH(1);

        private final int value;

        ModuleSeries(int value) {
            this.value = value;
        }

        /**
         * 获取模块类别
         * @param status
         * @return
         */
        public static ModuleSeries valueOf(JyResultStatus status) {
            return status.moduleSeries;
        }

        /**
         * 通过状态码获取所属模块
         * @param statusCode
         * @return
         */
        public static ModuleSeries valueOf(int statusCode) {
            ModuleSeries series = resolve(statusCode);
            if (series == null) {
                throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
            }
            return series;
        }

        /**
         * 将给定的状态代码解析为ModuleSeries
         * @param statusCode
         * @return
         */
        public static ModuleSeries resolve(int statusCode) {
            int seriesCode = statusCode / 1000;
            for (ModuleSeries series : values()) {
                if (series.value == seriesCode) {
                    return series;
                }
            }
            return null;
        }
    }
}
