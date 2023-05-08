package com.jyadmin.consts;

/**
 * 全局常量
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-05 21:00 <br>
 * @description: JyGlobalConstants <br>
 */
public class GlobalConstants {
    // ======================= 特殊字符 START =======================
    // 特殊字符 "."
    public static final String SYS_SPECIAL_CHARACTER_DOT = ".";
    // ======================= 特殊字符 END =======================

    // ======================= 访问URL START =======================
    // 登录请求路径 /api/auth/login
    public static final String SYS_LOGIN_URI = "/api/auth/login";


    // ======================= 访问URL END =======================

    // SECURITY_CONTEXT
    public static final String SYS_SECURITY_CONTEXT = "SECURITY_CONTEXT";

    // ======================= 登录TOKEN START =======================
    // Token 请求头标签名
    public static final String SYS_LOGIN_TOKEN_PARAM_NAME = "X-Token";
    // RefreshToken 请求头标签名
    public static final String SYS_LOGIN_REFRESH_TOKEN_PARAM_NAME = "X-RefreshToken";
    // ======================= 登录TOKEN END =======================

    // ======================= 登录验证码 START =======================
    // 登录验证码宽度
    public static final Integer SYS_CAPTCHA_WIDTH = 200;
    // 登录验证码高度
    public static final Integer SYS_CAPTCHA_HEIGHT = 45;
    // 验证码参与计算最大数字位数
    public static final Integer SYS_CAPTCHA_MATH_GENERATOR_NUMBER_LENGTH = 1;
    // ======================= 登录验证码 END =======================

    // ======================= 系统菜单 START =======================
    // 顶级菜单父节点ID为0
    public static final String SYS_MENU_ROOT_PARENT_ID = "0";
    // ======================= 系统菜单 END =======================

    // ======================= 系统短信、邮件、附件配置 START =======================
    // 系统附件配置ID
    public static final String SYS_FILE_CONFIG_ID = "1";
    // 系统短信配置ID
    public static final String SYS_SMS_CONFIG_ID = "2";
    // 系统邮件配置ID
    public static final String SYS_EMAIL_CONFIG_ID = "3";
    // 系统国家电话默认前缀
    public static final String SYS_PHONE_NUMBER_PREFIX = "+86";
    // 系统短信验证码默认前缀
    public static final String SYS_SMS_VERIFICATION_CODE_PREFIX = "sms_verification_code";
    // 系统短信验证码默认长度
    public static final Integer SYS_SMS_VERIFICATION_CODE_LENGTH = 4;
    // 系统短信验证码默认保存时限(秒s)
    public static final Integer SYS_SMS_VERIFICATION_CODE_TIME_LIMIT = 5 * 60;
    // ======================= 系统短信、邮件、附件配置 END =======================

    // ======================= 系统部门管理 START =======================
    // 系统部门顶级部门常量值
    public static final Integer SYS_DEPARTMENT_IS_ROOT = 1;
    // ======================= 系统部门管理 END =======================




    // ======================= 枚举 START =======================
    /**
     * 系统短信配置枚举类
     */
    public enum SysSmsConfigId {
        VERIFICATION_CODE("验证码", "VERIFICATION_CODE", "用于发送验证码的模板"),
        SIGN_IN("注册成功提示", "SIGN_IN", "用于发送注册成功提示的模板");

        // 名称
        private String name;
        // 编码
        private String code;
        // 备注说明
        private String remark;

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }

        public String getRemark() {
            return remark;
        }

        SysSmsConfigId(String name, String code, String remark) {
            this.name = name;
            this.code = code;
            this.remark = remark;
        }
    }

    /**
     * 角色接口权限（api_permission_portion=根据角色菜单限制接口权限， api_permission_all=拥有全部接口权限）
     * 该枚举类与字典对应 sys_role_api_permission
     */
    public enum SysApiPermission {
        API_PERMISSION_PORTION("根据角色菜单限制接口权限", "api_permission_portion", "根据角色菜单限制接口权限"),
        API_PERMISSION_ALL("拥有全部接口权限", "api_permission_all", "拥有全部接口权限");

        // 名称
        private String name;
        // 编码
        private String code;
        // 备注说明
        private String remark;

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }

        public String getRemark() {
            return remark;
        }

        SysApiPermission(String name, String code, String remark) {
            this.name = name;
            this.code = code;
            this.remark = remark;
        }
    }

    /**
     * status枚举类 1=启用 0=禁用
     */
    public enum SysStatus {
        ON("启用", 1),
        OFF("禁用", 0);

        // 名称
        private String name;
        // 值
        private Integer value;

        public String getName() {
            return name;
        }

        public Integer getValue() {
            return value;
        }

        SysStatus(String name, Integer value) {
            this.name = name;
            this.value = value;
        }
    }

    /**
     * deleted枚举类 1=删除 0=未删除
     */
    public enum SysDeleted {
        DELETED("删除", 1),
        EXIST("未删除", 0);

        // 名称
        private String name;
        // 值
        private Integer value;

        public String getName() {
            return name;
        }

        public Integer getValue() {
            return value;
        }

        SysDeleted(String name, Integer value) {
            this.name = name;
            this.value = value;
        }
    }

    /**
     * 执行状态枚举类 1=成功 0=失败
     */
    public enum SysExecuteStatus {
        SUCCESS("成功", 1),
        FAIL("失败", 0);

        // 名称
        private String name;
        // 值
        private Integer value;

        public String getName() {
            return name;
        }

        public Integer getValue() {
            return value;
        }

        SysExecuteStatus(String name, Integer value) {
            this.name = name;
            this.value = value;
        }
    }

    /**
     * 是否根节点枚举类 1=根节点 0=非根节点
     */
    public enum SysRootNode {
        ROOT("根节点", 1),
        NOT_ROOT("非根节点", 0);

        // 名称
        private String name;
        // 值
        private Integer value;

        public String getName() {
            return name;
        }

        public Integer getValue() {
            return value;
        }

        SysRootNode(String name, Integer value) {
            this.name = name;
            this.value = value;
        }
    }

    // ======================= 枚举 END =======================
}
