package com.jyblog.domain;

import com.jyblog.consts.JyBusinessStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 统一返回值
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-05 16:09 <br>
 * @description: Result <br>
 */
@ApiModel(description = "统一返回值")
@Data
@Accessors(chain = true)
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 97792549823353463L;

    @ApiModelProperty(value = "状态码", name = "code")
    private Integer code;

    @ApiModelProperty(value = "执行状态", name = "success")
    private Boolean success;

    @ApiModelProperty(value = "响应消息", name = "msg")
    private String msg;

    @ApiModelProperty(value = "响应数据", name = "data")
    private T data;

    public Result() {}

    public Result(Integer code, Boolean success, String msg) {
        this.code = code;
        this.success = success;
        this.msg = msg;
    }

    public Result(Integer code, Boolean success, String msg, T data) {
        this.code = code;
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public Result(JyBusinessStatus status, Boolean success, T data) {
        this.code = status.getValue();
        this.msg = status.getReasonPhrase();
        this.success = success;
        this.data = data;
    }

    public Result(JyBusinessStatus status, String msg, Boolean success, T data) {
        this.code = status.getValue();
        this.msg = msg;
        this.success = success;
        this.data = data;
    }

    public static <T> Result<T> ok() {
        return new Result<>(JyBusinessStatus.SUCCESS, true, null);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(JyBusinessStatus.SUCCESS, true, data);
    }

    public static <T> Result<T> ok(Integer code, String msg) {
        return new Result<>(code, true, msg);
    }

    public static <T> Result<T> ok(Integer code, String msg, T data) {
        return new Result<>(code, true, msg, data);
    }

    public static <T> Result<T> ok(JyBusinessStatus status) {
        return new Result<>(status, true, null);
    }

    public static <T> Result<T> ok(JyBusinessStatus status, String msg) {
        return new Result<>(status, msg, true, null);
    }

    public static <T> Result<T> ok(JyBusinessStatus status, T data) {
        return new Result<>(status, true, data);
    }

    public static <T> Result<T> ok(JyBusinessStatus status, String msg, T data) {
        return new Result<>(status, msg, true, data);
    }

    public static <T> Result<T> fail() {
        return new Result<>(JyBusinessStatus.FAIL, false, null);
    }

    public static <T> Result<T> fail(T data) {
        return new Result<>(JyBusinessStatus.FAIL, false, data);
    }

    public static <T> Result<T> fail(Integer code, String msg) {
        return new Result<>(code, false, msg);
    }

    public static <T> Result<T> fail(Integer code, String msg, T data) {
        return new Result<>(code, false, msg, data);
    }

    public static <T> Result<T> fail(JyBusinessStatus status) {
        return new Result<T>(status, false, null);
    }

    public static <T> Result<T> fail(JyBusinessStatus status, String msg) {
        return new Result<T>(status, msg, false, null);
    }

    public static <T> Result<T> fail(JyBusinessStatus status, T data) {
        return new Result<>(status, false, data);
    }

    public static <T> Result<T> fail(JyBusinessStatus status, String msg, T data) {
        return new Result<>(status, msg, false, data);
    }

    public static <T> Result<T> build() {
        return new Result<>();
    }

    public static <T> Result<T> build(Integer code, Boolean success, String msg) {
        return new Result<>(code, success, msg);
    }

    public static <T> Result<T> build(Integer code, Boolean success, String msg, T data) {
        return new Result<>(code, success, msg, data);
    }

    public static <T> Result<T> build(JyBusinessStatus status) {
        return new Result<>(status, null, null);
    }

    public static <T> Result<T> build(JyBusinessStatus status, String msg) {
        return new Result<>(status, msg, null, null);
    }

    public static <T> Result<T> build(JyBusinessStatus status, Boolean success, T data) {
        return new Result<>(status, success, data);
    }

    public static <T> Result<T> build(JyBusinessStatus status, String msg, Boolean success, T data) {
        return new Result<>(status, msg, success, data);
    }

    public static <T> Result<T> build(JyBusinessStatus status, Boolean success) {
        return new Result<>(status, success, null);
    }

    public static <T> Result<T> build(JyBusinessStatus status, String msg, Boolean success) {
        return new Result<>(status, msg, success, null);
    }

    public Result<T> status(JyBusinessStatus status) {
        this.code = status.getValue();
        this.msg = status.getReasonPhrase();
        return this;
    }

    public Result<T> status(Integer code) {
        this.code = code;
        return this;
    }

    public Result<T> msg(String msg) {
        this.msg = msg;
        return this;
    }

    public Result<T> success(Boolean success) {
        this.success = success;
        return this;
    }

    public Result<T> data(T data) {
        this.data = data;
        return this;
    }


}
