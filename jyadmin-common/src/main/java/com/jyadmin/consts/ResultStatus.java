package com.jyadmin.consts;

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
public enum ResultStatus {

    // region--- 0000 xxxx 基础状态码 ---
    /**
     * 0000 0200 成功
     */
    SUCCESS(200, ModuleSeries.BASE, "操作成功"),

    /**
     * 0000 0400 失败
     */
    FAIL(400, ModuleSeries.BASE, "服务异常"),
    // endregion------------------------

    // region--- 0001 xxxx 权限相关状态码 ---
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
    NOT_FOUND_LOGIN_INFO(10009, ModuleSeries.AUTH, "找不到当前登录的信息"),

    /**
     * 0001 0010 访问次数超出限制
     */
    LIMIT_EXCEEDED(10010, ModuleSeries.AUTH, "访问次数超出限制"),

    /**
     * 0001 0011 重复操作
     */
    REPEAT_OPERATION(10011, ModuleSeries.AUTH, "请勿重复操作"),

    /**
     * 0001 10012 验证码获取失败
     */
    CAPTCHA_FETCH_FAIL(10012, ModuleSeries.AUTH, "验证码获取失败"),

    /**
     * 0001 0013 验证码过期
     */
    CAPTCHA_EXPIRED(10013, ModuleSeries.AUTH, "验证码过期"),

    /**
     * 0001 0014 验证码输入错误
     */
    CAPTCHA_INPUT_ERROR(10014, ModuleSeries.AUTH, "验证码输入错误"),

    /**
     * 0001 0015 RefreshToken获取异常
     */
    REFRESH_TOKEN_ERROR(10015, ModuleSeries.AUTH, "RefreshToken获取异常"),



    // endregion--------------------

    // region--- 0002 xxxx 参数错误相关状态码 ---
    /**
     * 0002 0001
     */
    PARAM_ERROR(20001, ModuleSeries.PARAM, "请求参数错误"),

    // endregion---------------------

    // region--- 0005 xxxx 附件相关状态码 ---
    /**
     * 0005 0001
     */
    FILE_UPLOAD_FAIL(50001, ModuleSeries.FILE, "文件上传失败"),

    /**
     * 0005 0002
     */
    FILE_DOWNLOAD_FAIL(50002, ModuleSeries.FILE, "文件下载失败"),

    /**
     * 0005 0003
     */
    FILE_REMOVE_FAIL(50003, ModuleSeries.FILE, "文件删除失败"),
    // endregion---------------------

    // region--- 0006 xxxx 短信相关状态码 ---
    /**
     * 0006 0001
     */
    SMS_SEND_FAIL(60001, ModuleSeries.SMS, "短信发送失败"),
    // endregion--------------------

    // region--- 0007 xxxx 邮件相关状态码 ---
    /**
     * 0007 0001 邮件发送失败
     */
    EMAIL_SEND_FAIL(70001, ModuleSeries.EMAIL, "邮件发送失败")

    // endregion--------------------------
    ;

    // 状态码
    private final int value;

    // 模块类别
    private final ModuleSeries moduleSeries;

    // 提示
    private final String reasonPhrase;

    ResultStatus(int value, ModuleSeries moduleSeries, String reasonPhrase) {
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
        AUTH(1),
        // 2 参数异常
        PARAM(2),
        // 5 附件
        FILE(5),
        // 6 短信
        SMS(6),
        // 7 邮件
        EMAIL(7);

        private final int value;

        ModuleSeries(int value) {
            this.value = value;
        }

        /**
         * 获取模块类别
         * @param status
         * @return
         */
        public static ModuleSeries valueOf(ResultStatus status) {
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
            int seriesCode = statusCode / 10000;
            for (ModuleSeries series : values()) {
                if (series.value == seriesCode) {
                    return series;
                }
            }
            return null;
        }
    }
}
