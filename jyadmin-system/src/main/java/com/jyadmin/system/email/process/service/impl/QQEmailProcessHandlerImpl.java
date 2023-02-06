package com.jyadmin.system.email.process.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.jyadmin.consts.ResultStatus;
import com.jyadmin.exception.ApiException;
import com.jyadmin.system.config.detail.domain.ConfigDetail;
import com.jyadmin.system.config.detail.service.ConfigDetailService;
import com.jyadmin.system.config.module.domain.ModuleConfigWrapper;
import com.jyadmin.system.email.process.domain.EmailProcess;
import com.jyadmin.system.email.process.model.dto.EmailSendDTO;
import com.jyadmin.system.email.process.service.EmailProcessHandler;
import com.jyadmin.system.email.record.domain.EmailRecord;
import com.jyadmin.system.email.record.service.EmailRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-22 23:57 <br>
 * @description: QQEmailProcessHandlerImpl <br>
 */
@Slf4j
@Service("qqEmailProcessHandler")
public class QQEmailProcessHandlerImpl implements EmailProcessHandler {

    @Resource
    private EmailRecordService emailRecordService;

    @Resource
    private ConfigDetailService configDetailService;

    /**
     * 构建JavaMailSender
     * @param emailConfigWrapper 配置包装类
     * @return JavaMailSender
     */
    private JavaMailSender buildJavaMailSender (ModuleConfigWrapper emailConfigWrapper) {
        ConfigDetail configDetail = emailConfigWrapper.getConfigDetail();
        String host = configDetailService.getValueByCode(configDetail, "host");
        String port = configDetailService.getValueByCode(configDetail, "port");
        String username = configDetailService.getValueByCode(configDetail, "username");
        String password = configDetailService.getValueByCode(configDetail, "password");
        String timeout = configDetailService.getValueByCode(configDetail, "timeout");
        String auth = configDetailService.getValueByCode(configDetail, "auth");
        String socketFactory = configDetailService.getValueByCode(configDetail, "socketFactory");

        JavaMailSenderImpl javaMailSender =new JavaMailSenderImpl();
        javaMailSender.setDefaultEncoding("UTF-8");
        javaMailSender.setHost(host);
        javaMailSender.setPort(Integer.parseInt(port));
        javaMailSender.setProtocol(JavaMailSenderImpl.DEFAULT_PROTOCOL);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        Properties p = new Properties();
        p.setProperty("mail.smtp.timeout", timeout);
        p.setProperty("mail.smtp.auth", auth);
        p.setProperty("mail.smtp.socketFactory.class", socketFactory);
        javaMailSender.setJavaMailProperties(p);

        log.debug("build javaMailSender success!");
        return javaMailSender;
    }

    @Transactional
    @Override
    public EmailProcess sendEmail(EmailSendDTO emailSendDTO, ModuleConfigWrapper emailConfigWrapper) {
        ConfigDetail configDetail = emailConfigWrapper.getConfigDetail();
        String from = configDetailService.getValueByCode(configDetail,"username");

        JavaMailSenderImpl javaMailSender = (JavaMailSenderImpl) buildJavaMailSender(emailConfigWrapper);

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            messageHelper.setFrom(from);
            messageHelper.setTo(emailSendDTO.getReceiver());
            messageHelper.setSubject(emailSendDTO.getSubject());
            messageHelper.setText(emailSendDTO.getBody(), true);
            javaMailSender.send(message);

        } catch (MessagingException e) {
            throw new ApiException(ResultStatus.EMAIL_SEND_FAIL);
        }

        // 保存邮件发送记录
        EmailRecord record = new EmailRecord();
        BeanUtil.copyProperties(emailSendDTO, record);
        record.setSender(from);
        record.setSource(emailConfigWrapper.getConfig().getStorageType());
        log.debug("record: {}", record);
        emailRecordService.save(record);
        return new EmailProcess().setId(record.getId());
    }


}
