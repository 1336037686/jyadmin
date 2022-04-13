package com.jyblog.exception;

import com.jyblog.consts.JyResultStatus;
import lombok.Data;

/**
 * 业务异常
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-05 02:16 <br>
 * @description: BusinessException <br>
 */
@Data
public class JyBusinessException extends RuntimeException {

    private Integer code;

    private String msg;

    public JyBusinessException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JyBusinessException(String message, Integer code, String msg) {
        super(message);
        this.code = code;
        this.msg = msg;
    }

    public JyBusinessException(Throwable cause, Integer code, String msg) {
        super(msg, cause);
        this.code = code;
        this.msg = msg;
    }

    public JyBusinessException(JyResultStatus status) {
        this.code = status.getValue();
        this.msg = status.getReasonPhrase();
    }

    public JyBusinessException(String message, JyResultStatus status) {
        super(message);
        this.code = status.getValue();
        this.msg = status.getReasonPhrase();
    }

    public JyBusinessException(Throwable cause, JyResultStatus status) {
        super(status.getReasonPhrase(), cause);
        this.code = status.getValue();
        this.msg = status.getReasonPhrase();
    }

}
