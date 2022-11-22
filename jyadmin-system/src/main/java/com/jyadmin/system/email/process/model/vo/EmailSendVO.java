package com.jyadmin.system.email.process.model.vo;

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
public class EmailSendVO implements Serializable {

    /**
     * 接收者
     */
    @ApiModelProperty(value = "接收者", name = "receiver")
    @NotBlank(message = "接收者不能为空")
    private String receiver;

    /**
     * 主题
     */
    @ApiModelProperty(value = "主题", name = "subject")
    @NotBlank(message = "主题不能为空")
    private String subject;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容", name = "body")
    private String body;

    /**
     * 业务类型
     */
    @ApiModelProperty(value = "业务类型", name = "relevance")
    @NotBlank(message = "业务类型不能为空")
    private String relevance;

}
