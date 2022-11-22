package com.jyadmin.system.email.process.service;

import com.jyadmin.system.email.process.domain.EmailProcess;
import com.jyadmin.system.email.process.model.dto.EmailSendDTO;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-22 23:37 <br>
 * @description: EmailProcessService <br>
 */
public interface EmailProcessService {

    EmailProcess sendEmail(EmailSendDTO emailSendDTO);

}
