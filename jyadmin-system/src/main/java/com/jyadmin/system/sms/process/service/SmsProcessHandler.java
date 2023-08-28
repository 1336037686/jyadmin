package com.jyadmin.system.sms.process.service;

import com.jyadmin.system.config.module.domain.ModuleConfigWrapper;
import com.jyadmin.system.sms.process.domain.SmsProcess;
import com.jyadmin.system.sms.process.model.dto.SmsSendDTO;
import org.apache.poi.ss.formula.functions.T;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-22 23:45 <br>
 * @description: SmsProcessHandler <br>
 */
public interface SmsProcessHandler {

    /**
     * 短信发送处理
     * @param smsSendDTO 短信发送DTO
     * @param smsConfigWrapper 配置详情
     * @return EmailProcess
     */
    SmsProcess sendSms(SmsSendDTO smsSendDTO, ModuleConfigWrapper smsConfigWrapper);
}
