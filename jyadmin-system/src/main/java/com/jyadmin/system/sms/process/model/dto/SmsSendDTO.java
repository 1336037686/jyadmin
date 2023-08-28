package com.jyadmin.system.sms.process.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.poi.ss.formula.functions.T;

import java.io.Serializable;
import java.util.List;

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
     * 唯一ID
     */
    private String uniqueId;

    /**
     * 发送短信类别
     * SysSmsTemplate 枚举
     *
     */
    private String type;

    /**
     * 接收者
     */
    private String receiver;

    /**
     * 内容 json
     */
    private List<SmsBody> body;

    /**
     * 业务类型
     */
    private String relevance;


    /**
     * 内容
     */
    @Data
    @Accessors(chain = true)
    public static class SmsBody {

        /**
         * key
         */
        private String key;

        /**
         * value
         */
        private String value;

    }

}
