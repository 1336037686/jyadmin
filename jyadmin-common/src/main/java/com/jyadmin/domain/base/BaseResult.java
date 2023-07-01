package com.jyadmin.domain.base;

import com.jyadmin.consts.ResultStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 基础返回结果
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-06-29 16:04 <br>
 * @description: BaseResult <br>
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class BaseResult implements Serializable {

    private static final long serialVersionUID = 1;

    @ApiModelProperty(value = "状态码", name = "code")
    private Integer code;

    @ApiModelProperty(value = "执行状态", name = "success")
    private Boolean success;

    @ApiModelProperty(value = "响应消息", name = "msg")
    private String msg;

    public BaseResult setStatus(ResultStatus status) {
        this.code = status.getValue();
        this.msg = status.getReasonPhrase();
        return this;
    }

    public BaseResult setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

}
