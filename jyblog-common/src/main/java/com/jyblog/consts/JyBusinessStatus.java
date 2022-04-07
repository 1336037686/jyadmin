package com.jyblog.consts;

/**
 * 业务状态码
 * 00 000 000
 * 前两位，表示业务类别
 * 3至5位，表示具体模块
 * 6至8位，表示具体状态
 *
 * 通用成功：00 000 001
 * 通用失败：00 000 000
 *
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-05 16:12 <br>
 * @description: BusinessStatus <br>
 */
public enum JyBusinessStatus {

    // --- 00 xxx xxx 基础状态码 ---

    /**
     * 00 000 200 成功
     */
    SUCCESS(200, BusinessSeries.JYCOMMON, ModuleSeries.JYCOMMON_BASE, "成功"),

    /**
     * 00 000 400 失败
     */
    FAIL(400, BusinessSeries.JYCOMMON, ModuleSeries.JYCOMMON_BASE, "失败"),

    ;

    // 状态码
    private final int value;

    // 业务类别
    private final BusinessSeries businessSeries;

    // 模块类别
    private final ModuleSeries moduleSeries;

    // 提示
    private final String reasonPhrase;

    JyBusinessStatus(int value, BusinessSeries businessSeries, ModuleSeries moduleSeries, String reasonPhrase) {
        this.value = value;
        this.businessSeries = businessSeries;
        this.moduleSeries = moduleSeries;
        this.reasonPhrase = reasonPhrase;
    }

    public int getValue() {
        return value;
    }

    public BusinessSeries getBusinessSeries() {
        return businessSeries;
    }

    public ModuleSeries getModuleSeries() {
        return moduleSeries;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    /**
     * 业务类别枚举
     */
    public enum BusinessSeries {

        // 基础状态 00
        JYCOMMON(0),
        // 系统业务 01
        JYSYSTEM(1),
        // 博客业务 02
        JYBLOG(2);

        private final int value;

        BusinessSeries(int value) {
            this.value = value;
        }

        /**
         * 获取业务类别
         * @param status
         * @return
         */
        public static BusinessSeries valueOf(JyBusinessStatus status) {
            return status.businessSeries;
        }

        /**
         * 通过状态码获取业务类别
         * @param statusCode
         * @return
         */
        public static BusinessSeries valueOf(int statusCode) {
            BusinessSeries series = resolve(statusCode);
            if (series == null) {
                throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
            }
            return series;
        }

        /**
         * 将给定的状态代码解析为BusinessSeries
         * @param statusCode
         * @return
         */
        public static BusinessSeries resolve(int statusCode) {
            int seriesCode = statusCode / 1000000;
            for (BusinessSeries series : values()) {
                if (series.value == seriesCode) {
                    return series;
                }
            }
            return null;
        }

    }

    /**
     * 模块类别枚举
     */
    public enum ModuleSeries {

        // 00 000
        JYCOMMON_BASE(0);

        private final int value;

        ModuleSeries(int value) {
            this.value = value;
        }

        /**
         * 获取模块类别
         * @param status
         * @return
         */
        public static ModuleSeries valueOf(JyBusinessStatus status) {
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
