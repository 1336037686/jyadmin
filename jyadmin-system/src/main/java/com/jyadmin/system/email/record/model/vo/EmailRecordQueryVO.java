package com.jyadmin.system.email.record.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.jyadmin.domain.base.BasePageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 邮件发送记录
 */
@ApiModel("系统邮件发送记录-查找-数据模型")
@Data
public class EmailRecordQueryVO extends BasePageVO implements Serializable {


    /**
     * 发送人
     */
    @ApiModelProperty(value = "发送人", name = "sender")
    private String sender;

    /**
     * 接收人
     */
    @ApiModelProperty(value = "接收人", name = "receiver")
    private String receiver;

    /**
     * 主题
     */
    @ApiModelProperty(value = "主题", name = "subject")
    private String subject;

    /**
     * 邮件平台
     */
    @ApiModelProperty(value = "邮件平台", name = "source")
    private String source;

    /**
     * 业务标识
     */
    @ApiModelProperty(value = "业务标识", name = "relevance")
    private String relevance;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}