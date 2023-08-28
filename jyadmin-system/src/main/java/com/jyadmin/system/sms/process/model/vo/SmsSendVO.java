package com.jyadmin.system.sms.process.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-22 23:27 <br>
 * @description: Email <br>
 */
@Data
@Accessors(chain = true)
public class SmsSendVO implements Serializable {

    /**
     * 唯一ID
     */
    private String uniqueId;

    /**
     * 接收者
     */
    @ApiModelProperty(value = "接收者", name = "receiver")
    @NotBlank(message = "接收者不能为空")
    private String receiver;

    /**
     * 业务类型
     */
    @ApiModelProperty(value = "业务类型", name = "relevance")
    @NotBlank(message = "业务类型不能为空")
    private String relevance;

}
