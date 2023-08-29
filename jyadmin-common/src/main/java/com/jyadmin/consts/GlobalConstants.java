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

    // ======================= 用户基础信息设置 START =======================

    // 默认用户密码
    public static final String DEFAULT_USER_PASSWORD = "jyadmin123";

    // 默认用户角色编码
    public static final String DEFAULT_USER_ROLE = "member";

    // 默认用户头像
    public static final String DEFAULT_USER_AVATAR = "https://himg.bdimg.com/sys/portrait/item/pp.1.7cc3463a.Sy3bpWfCP6umkh2-3oWVVg.jpg";

    // 默认用户随机昵称生成长度
    public static final Integer DEFAULT_USER_NICKNAME_GENERATE_LENGTH = 15;

    // ======================= 用户基础信息设置 END =======================

    // ======================= 登录TOKEN START =======================

    // JWT TOKEN 载荷 自定义字段
    public static final String SYS_JWT_TOKEN_PAYLOAD_USERNAME = "username";

    // SECURITY_CONTEXT
    public static final String SYS_SECURITY_CONTEXT = "SECURITY_CONTEXT";

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
    public static final Long SYS_MENU_ROOT_PARENT_ID = 0L;

    // ======================= 系统菜单 END =======================

    // ======================= 系统短信、邮件、附件配置 START =======================

    // 系统附件配置ID
    public static final Long SYS_FILE_CONFIG_ID = 1L;

    // 系统短信配置ID
    public static final Long SYS_SMS_CONFIG_ID = 2L;

    // 系统邮件配置ID
    public static final Long SYS_EMAIL_CONFIG_ID = 3L;

    // 文件上传 本地上传 文件存储平台
    public static final String SYS_FILE_SOURCE_LOACL = "local-oss";

    // 文件上传 本地上传 基础路径配置 编码
    public static final String SYS_FILE_LOCAL_CONFIG_BASE_PATH = "basePath";

    // 系统国家电话默认前缀
    public static final String SYS_PHONE_NUMBER_PREFIX = "+86";

    // 系统短信验证码默认前缀
    public static final String SYS_SMS_VERIFICATION_CODE_PREFIX = "sys_sms:verification_code";

    // 系统短信验证码默认长度
    public static final Integer SYS_SMS_VERIFICATION_CODE_LENGTH = 4;

    // 系统短信验证码默认保存时限(秒s)
    public static final Integer SYS_SMS_VERIFICATION_CODE_TIME_LIMIT = 5 * 60;

    // ======================= 系统短信、邮件、附件配置 END =======================

    // ======================= 系统部门管理 START =======================

    // 系统部门顶级部门常量值
    public static final Integer SYS_DEPARTMENT_IS_ROOT = 1;

    // ======================= 系统部门管理 END =======================

    // ======================= 运行时日志 START =======================

    public static final String SYS_RUNTIME_LOG_TYPE_DIR = "dir";
    public static final String SYS_RUNTIME_LOG_TYPE_FILE = "file";

    // ======================= 运行时日志 END =======================

    // ======================= 角色数据范围 START =======================

    // 角色数据范围-全部
    public static final String SYS_ROLE_DATA_SCOPE_ALL = "all";

    // 角色数据范围-本级
    public static final String SYS_ROLE_DATA_SCOPE_LOCAL = "local";

    // 角色数据范围-自定义
    public static final String SYS_ROLE_DATA_SCOPE_OTHER = "other";

    // ======================= 角色数据范围 END =======================

    // ======================= 枚举 START =======================

    /**
     * 用户账号类型枚举类
     */
    public enum SysUserType {
        MEMBER("会员账号", 2),
        ADMIN("管理员账号", 1),
        NORMAL("普通用户账号", 0);

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

        SysUserType(String name, Integer value) {
            this.name = name;
            this.value = value;
        }
    }

    /**
     * 系统短信配置枚举类
     */
    public enum SysSmsTemplate {
        VERIFICATION_CODE("验证码", "VERIFICATION_CODE", "用于发送验证码的模板");

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

        SysSmsTemplate(String name, String code, String remark) {
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
