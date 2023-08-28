package com.jyadmin.system.sms.process.service;

import com.jyadmin.system.sms.process.domain.SmsProcess;
import com.jyadmin.system.sms.process.model.dto.SmsSendDTO;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-22 23:37 <br>
 * @description: EmailProcessService <br>
 */
public interface SmsProcessService {

    SmsProcess sendSms(SmsSendDTO smsSendDTO);

    SmsProcess sendVerificationCode(SmsSendDTO smsSendDTO);

}
