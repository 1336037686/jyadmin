package com.jyadmin.system.sms.record.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.jyadmin.domain.base.BasePageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 邮件发送记录
 */
@ApiModel("系统短信发送记录-查找-数据模型")
@Data
public class SmsRecordQueryVO extends BasePageVO implements Serializable {


    /**
     * 电话
     */
    @ApiModelProperty(value = "电话", name = "phone")
    private String phone;

    /**
     * 短信平台
     */
    @ApiModelProperty(value = "短信平台", name = "source")
    private String source;

    /**
     * 业务标识
     */
    @ApiModelProperty(value = "业务标识", name = "relevance")
    private String relevance;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}