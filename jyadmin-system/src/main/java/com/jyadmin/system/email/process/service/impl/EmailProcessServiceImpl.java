package com.jyadmin.system.email.process.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.jyadmin.consts.GlobalConstants;
import com.jyadmin.system.config.detail.domain.ConfigDetail;
import com.jyadmin.system.config.detail.domain.ConfigDetailJsonModel;
import com.jyadmin.system.config.module.domain.ModuleConfig;
import com.jyadmin.system.config.module.domain.ModuleConfigWrapper;
import com.jyadmin.system.config.module.service.ModuleConfigService;
import com.jyadmin.system.email.process.domain.EmailProcess;
import com.jyadmin.system.email.process.model.dto.EmailSendDTO;
import com.jyadmin.system.email.process.service.EmailProcessHandler;
import com.jyadmin.system.email.process.service.EmailProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-22 23:41 <br>
 * @description: EmailProcessServiceImpl <br>
 */
@Slf4j
@Service
public class EmailProcessServiceImpl  implements EmailProcessService {

    @Resource
    private ModuleConfigService moduleConfigService;

    /**
     * 获取当前的附件配置
     * @return ConfigDetail 配置详情
     */
    private ModuleConfigWrapper getEnableEmailConfigDetail() {
        return moduleConfigService.getEnableConfigDetail(GlobalConstants.SYS_EMAIL_CONFIG_ID);
    }


    @Override
    public EmailProcess sendEmail(EmailSendDTO emailSendDTO) {
        ModuleConfigWrapper emailConfigWrapper = getEnableEmailConfigDetail();
        ModuleConfig config = emailConfigWrapper.getConfig();
        ConfigDetail configDetail = emailConfigWrapper.getConfigDetail();

        ConfigDetailJsonModel beanInfo = configDetail.getJsonObjs().stream()
                .filter(x -> "bean".equals(x.getCode())).findFirst().orElse(null);
        Assert.notNull(beanInfo, "缺少Bean配置！");

        // 获取配置相对应的处理器
        EmailProcessHandler emailProcessHandler = null;
        String storageType = config.getStorageType(); // 获取托管平台
        if ("qq-email".equals(storageType)) emailProcessHandler = SpringUtil.getBean(beanInfo.getValue());
        if ("wangyi-email".equals(storageType)) emailProcessHandler = SpringUtil.getBean(beanInfo.getValue());
        Assert.notNull(emailProcessHandler, "未找到相应处理器！");

        return emailProcessHandler.sendEmail(emailSendDTO, emailConfigWrapper);
    }


}
