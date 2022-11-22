package com.jyadmin.system.email.process.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-22 23:27 <br>
 * @description: Email <br>
 */
@Data
@Accessors(chain = true)
public class EmailSendDTO implements Serializable {

    /**
     * 接收者
     */
    private String receiver;

    /**
     * 主题
     */
    private String subject;

    /**
     * 内容
     */
    private String body;

    /**
     * 业务类型
     */
    private String relevance;

}
