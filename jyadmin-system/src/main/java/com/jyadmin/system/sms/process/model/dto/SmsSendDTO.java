package com.jyadmin.system.sms.process.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-22 23:27 <br>
 * @description: Sms <br>
 */
@Data
@Accessors(chain = true)
public class SmsSendDTO implements Serializable {

    /**
     * 发送短信类别
     * SysSmsConfigId 枚举
     *
     */
    private String type;

    /**
     * 接收者
     */
    private String[] receiver;

    /**
     * 内容
     */
    private String[] body;

    /**
     * 业务类型
     */
    private String relevance;

}
