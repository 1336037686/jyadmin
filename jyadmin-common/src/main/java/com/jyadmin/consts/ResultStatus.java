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

    // region--- 2200 xxxx 基础系统设施 ---
    /**
     * 2200 0001 访问次数超出限制
     */
    LIMIT_EXCEEDED(22000001, ModuleSeries.SYS_INFRASTRUCTURE, "访问次数超出限制"),

    /**
     * 2200 0002 重复操作
     */
    REPEAT_OPERATION(22000002, ModuleSeries.SYS_INFRASTRUCTURE, "请勿重复操作"),

    /**
     * 2200 0003 演示系统，不允许操作
     */
    DEMO_SYSTEM_NOT_ALLOW_OPERATION(22000003, ModuleSeries.SYS_INFRASTRUCTURE, "当前系统为演示系统，不允许操作"),
    // endregion------------------------

    // region--- 2201 xxxx 权限相关状态码 ---
    /**
     * 2201 0001 权限不足
     */
    INSUFFICIENT_PERMISSIONS(22010001, ModuleSeries.AUTH, "权限不足"),

    /**
     * 2201 0002 请求未授权
     */
    REQUEST_NOT_AUTHORIZED(22010002, ModuleSeries.AUTH, "请求未授权"),

    /**
     * 2201 0003 账号被锁定
     */
    ACCOUNT_LOCKOUT(22010003, ModuleSeries.AUTH, "账号被锁定，请在1小时后重试"),

    /**
     * 2201 0004 密码过期
     */
    PASSWORD_EXPIRATION(22010004, ModuleSeries.AUTH, "密码过期，请联系管理员"),

    /**
     * 2201 0005 账户过期
     */
    ACCOUNT_EXPIRATION(22010005, ModuleSeries.AUTH, "账户过期，请联系管理员"),

    /**
     * 2201 0006 账户被禁用
     */
    ACCOUNT_DISABLED(22010006, ModuleSeries.AUTH, "账户被禁用，请联系管理员"),

    /**
     * 2201 0007 用户名或者密码输入错误
     */
    USERNAME_PASSWORD_ERROR(22010007, ModuleSeries.AUTH, "用户名或者密码输入错误，请重新输入"),

    /**
     * 2201 0008 当前登录状态过期
     */
    LOGIN_STATUS_EXPIRED(22010008, ModuleSeries.AUTH, "登录过期，请重新登录"),

    /**
     * 2201 0009 找不到当前登录的信息
     */
    NOT_FOUND_LOGIN_INFO(22010009, ModuleSeries.AUTH, "找不到当前登录的信息"),

    /**
     * 2201 0010 登陆续期失败
     */
    REFRESH_TOKEN_ERROR(22010010, ModuleSeries.AUTH, "登陆续期失败"),

    /**
     * 2201 0011 登录信息获取异常
     */
    LOGIN_INFO_OBTAIN_ERROR(22010011, ModuleSeries.AUTH, "登录信息获取异常"),

    /**
     * 2201 0012 RefreshToken不存在
     */
    REFRESH_TOKEN_NOT_EXIST(22010012, ModuleSeries.AUTH, "RefreshToken不存在"),

    /**
     * 2201 0013 Token不存在
     */
    TOKEN_NOT_EXIST(22010013, ModuleSeries.AUTH, "Token不存在"),

    /**
     * 2201 0014 Token过期
     */
    TOKEN_EXPIR_ERROR(22010014, ModuleSeries.AUTH, "登录过期，请重新登录"),

    /**
     * 2201 0015 Token格式异常
     */
    TOKEN_FORMAT_ERROR(22010015, ModuleSeries.AUTH, "Token格式异常"),

    /**
     * 2201 0016 异地登录
     */
    REMOTE_LOGIN_ERROR(22010016, ModuleSeries.AUTH, "该账号已在别处登录"),

    /**
     * 2201 0017 当前账户已存在
     */
    ACCOUNT_ALREADY_EXIST(22010017, ModuleSeries.AUTH, "当前账户已存在"),

    // endregion--------------------

    // region--- 2202 xxxx 参数错误相关状态码 ---
    /**
     * 2202 0001 请求参数错误
     */
    PARAM_ERROR(22020001, ModuleSeries.PARAM, "请求参数错误"),

    /**
     * 2202 0002 XSS过滤 存在敏感参数信息，不允许访问
     */
    PARAM_XSS_SENSITIVITY_INFO(22020002, ModuleSeries.PARAM, "存在敏感参数信息，不允许访问"),

    /**
     * 2202 0003 XSS过滤异常
     */
    PARAM_XSS_ERROR(22020003, ModuleSeries.PARAM, "XSS校验异常，请联系管理员解决"),

    // endregion---------------------

    // region--- 2203 xxxx 登录验证码 ---
    /**
     * 2203 0001 验证码获取失败
     */
    CAPTCHA_FETCH_FAIL(22030001, ModuleSeries.LOGIN_CAPTCHA, "验证码获取失败"),

    /**
     * 2203 0002 验证码过期
     */
    CAPTCHA_EXPIRED(22030002, ModuleSeries.LOGIN_CAPTCHA, "验证码过期"),

    /**
     * 2203 0003 验证码输入错误
     */
    CAPTCHA_INPUT_ERROR(22030003, ModuleSeries.LOGIN_CAPTCHA, "验证码输入错误"),
    // endregion------------------------

    // region--- 2230 xxxx 附件相关状态码 ---
    /**
     * 2230 0001 文件上传失败
     */
    FILE_UPLOAD_FAIL(22300001, ModuleSeries.FILE, "文件上传失败"),

    /**
     * 2230 0002 文件下载失败
     */
    FILE_DOWNLOAD_FAIL(22300002, ModuleSeries.FILE, "文件下载失败"),

    /**
     * 2230 0003 文件删除失败
     */
    FILE_REMOVE_FAIL(22300003, ModuleSeries.FILE, "文件删除失败"),
    // endregion---------------------

    // region--- 2231 xxxx 短信相关状态码 ---
    /**
     * 2231 0001
     */
    SMS_SEND_FAIL(22310001, ModuleSeries.SMS, "短信发送失败"),
    // endregion--------------------

    // region--- 2232 xxxx 邮件相关状态码 ---
    /**
     * 2232 0001 邮件发送失败
     */
    EMAIL_SEND_FAIL(22320001, ModuleSeries.EMAIL, "邮件发送失败"),

    // endregion--------------------------

    // region--- 2299 xxxx 代码生成器相关状态码 ---
    /**
     * 2299 0001 获取数据库表元数据失败
     */
    CODE_GEN_QUERY_TABLE_METADATA_FAIL(22990001, ModuleSeries.EMAIL, "获取数据库表元数据失败"),

    /**
     * 2299 0002 获取数据库表字段元数据失败
     */
    CODE_GEN_QUERY_FIELD_METADATA_FAIL(22990002, ModuleSeries.EMAIL, "获取数据库表字段元数据失败"),

    /**
     * 2299 0003 当前数据库表记录已存在
     */
    CODE_GEN_TABLE_ALREADY_EXIST(22990003, ModuleSeries.EMAIL, "当前数据库表记录已存在"),

    /**
     * 2299 0004 当前数据库表数据加载失败
     */
    CODE_GEN_TABLE_LOAD_ERROR(22990004, ModuleSeries.EMAIL, "当前数据库表数据加载失败"),

    /**
     * 2299 0005 当前数据库表记录不存在
     */
    CODE_GEN_TABLE_ALREADY_INEXIST(22990005, ModuleSeries.EMAIL, "当前数据库表记录不存在"),

    /**
     * 2299 0006 代码生成失败
     */
    CODE_GEN_ERROR(22990006, ModuleSeries.EMAIL, "代码生成失败")

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
        // 2200 基础系统设置
        SYS_INFRASTRUCTURE(2200),
        // 2201 权限控制
        AUTH(2201),
        // 2202 参数校验
        PARAM(2202),
        // 2203 登录验证码
        LOGIN_CAPTCHA(2203),
        // 2230 附件上传
        FILE(2230),
        // 2231 短信发送
        SMS(2231),
        // 2232 邮件发送
        EMAIL(2232),
        // 2299 代码生成
        CODE_GEN(2299);

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
