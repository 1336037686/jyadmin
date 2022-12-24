package com.jyadmin.exception;

import com.jyadmin.consts.ResultStatus;
import lombok.Data;

/**
 * 业务异常
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-05 02:16 <br>
 * @description: BusinessException <br>
 */
@Data
public class ApiException extends RuntimeException {

    private Integer code;

    private String msg;

    public ApiException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ApiException(String message, Integer code, String msg) {
        super(message);
        this.code = code;
        this.msg = msg;
    }

    public ApiException(Throwable cause, Integer code, String msg) {
        super(msg, cause);
        this.code = code;
        this.msg = msg;
    }

    public ApiException(ResultStatus status) {
        this.code = status.getValue();
        this.msg = status.getReasonPhrase();
    }

    public ApiException(ResultStatus status, String message) {
        super(message);
        this.code = status.getValue();
        this.msg = message;
    }

    public ApiException(Throwable cause, ResultStatus status) {
        super(status.getReasonPhrase(), cause);
        this.code = status.getValue();
        this.msg = status.getReasonPhrase();
    }

}
