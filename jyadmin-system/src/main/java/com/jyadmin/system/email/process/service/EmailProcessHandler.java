package com.jyadmin.system.email.process.service;

import com.jyadmin.system.config.module.domain.ModuleConfigWrapper;
import com.jyadmin.system.email.process.domain.EmailProcess;
import com.jyadmin.system.email.process.model.dto.EmailSendDTO;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-22 23:45 <br>
 * @description: EmailProcessHandler <br>
 */
public interface EmailProcessHandler {

    /**
     * 邮件发送处理
     * @param emailSendDTO 邮件发送DTO
     * @param emailConfigWrapper 配置详情
     * @return EmailProcess
     */
    EmailProcess sendEmail(EmailSendDTO emailSendDTO, ModuleConfigWrapper emailConfigWrapper);
}
