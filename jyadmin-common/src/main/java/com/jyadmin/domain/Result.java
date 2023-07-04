package com.jyadmin.domain;

import com.jyadmin.consts.ResultStatus;
import com.jyadmin.domain.base.BaseResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 统一返回值
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-05 16:09 <br>
 * @description: Result <br>
 */
@ApiModel("统一返回值")
@Data
@Accessors(chain = true)
public class Result<T> extends BaseResult<T> implements Serializable {

    private static final long serialVersionUID = 97792549823353463L;

    @ApiModelProperty(value = "响应数据", name = "data")
    private T data;

    public Result() {}

    public Result(Integer code, Boolean success, String msg) {
        super(code, success, msg);
    }

    public Result(Integer code, Boolean success, String msg, T data) {
        super(code, success, msg);
        this.data = data;
    }

    public Result(ResultStatus status, Boolean success, T data) {
        super(status.getValue(), success, status.getReasonPhrase());
        this.data = data;
    }

    public Result(ResultStatus status, String msg, Boolean success, T data) {
        super(status.getValue(), success, msg);
        this.data = data;
    }

    public static <T> Result<T> ok() {
        return new Result<>(ResultStatus.SUCCESS, true, null);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(ResultStatus.SUCCESS, true, data);
    }

    public static <T> Result<T> ok(Integer code, String msg) {
        return new Result<>(code, true, msg);
    }

    public static <T> Result<T> ok(Integer code, String msg, T data) {
        return new Result<>(code, true, msg, data);
    }

    public static <T> Result<T> ok(ResultStatus status) {
        return new Result<>(status, true, null);
    }

    public static <T> Result<T> ok(ResultStatus status, String msg) {
        return new Result<>(status, msg, true, null);
    }

    public static <T> Result<T> ok(ResultStatus status, T data) {
        return new Result<>(status, true, data);
    }

    public static <T> Result<T> ok(ResultStatus status, String msg, T data) {
        return new Result<>(status, msg, true, data);
    }

    public static <T> Result<T> fail() {
        return new Result<>(ResultStatus.FAIL, false, null);
    }

    public static <T> Result<T> fail(T data) {
        return new Result<>(ResultStatus.FAIL, false, data);
    }

    public static <T> Result<T> fail(Integer code, String msg) {
        return new Result<>(code, false, msg);
    }

    public static <T> Result<T> fail(Integer code, String msg, T data) {
        return new Result<>(code, false, msg, data);
    }

    public static <T> Result<T> fail(ResultStatus status) {
        return new Result<T>(status, false, null);
    }

    public static <T> Result<T> fail(ResultStatus status, String msg) {
        return new Result<T>(status, msg, false, null);
    }

    public static <T> Result<T> fail(ResultStatus status, T data) {
        return new Result<>(status, false, data);
    }

    public static <T> Result<T> fail(ResultStatus status, String msg, T data) {
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

    public static <T> Result<T> build(ResultStatus status) {
        return new Result<>(status, null, null);
    }

    public static <T> Result<T> build(ResultStatus status, String msg) {
        return new Result<>(status, msg, null, null);
    }

    public static <T> Result<T> build(ResultStatus status, Boolean success, T data) {
        return new Result<>(status, success, data);
    }

    public static <T> Result<T> build(ResultStatus status, String msg, Boolean success, T data) {
        return new Result<>(status, msg, success, data);
    }

    public static <T> Result<T> build(ResultStatus status, Boolean success) {
        return new Result<>(status, success, null);
    }

    public static <T> Result<T> build(ResultStatus status, String msg, Boolean success) {
        return new Result<>(status, msg, success, null);
    }

    public Result<T> status(ResultStatus status) {
        super.setStatus(status);
        return this;
    }

    public Result<T> status(Integer code) {
        super.setCode(code);
        return this;
    }

    public Result<T> msg(String msg) {
        super.setMsg(msg);
        return this;
    }

    public Result<T> success(Boolean success) {
        super.setSuccess(success);
        return this;
    }

    public Result<T> data(T data) {
        this.data = data;
        return this;
    }


    @Override
    public T getResultData() {
        return this.data;
    }
}
