package com.jyadmin.consts;

/**
 * 全局常量
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-05 21:00 <br>
 * @description: JyGlobalConstants <br>
 */
public class GlobalConstants {

    // SECURITY_CONTEXT
    public static final String SYS_SECURITY_CONTEXT = "SECURITY_CONTEXT";
    // 系统附件配置ID
    public static final String SYS_FILE_CONFIG_ID = "1";
    // 系统短信配置ID
    public static final String SYS_SMS_CONFIG_ID = "2";
    // 系统邮件配置ID
    public static final String SYS_EMAIL_CONFIG_ID = "3";
    // 系统国家电话默认前缀
    public static final String SYS_PHONE_NUMBER_PREFIX = "+86";



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



}
