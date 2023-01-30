package com.jyadmin.system.sms.process.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.jyadmin.consts.GlobalConstants;
import com.jyadmin.system.config.detail.domain.ConfigDetail;
import com.jyadmin.system.config.detail.domain.ConfigDetailJsonModel;
import com.jyadmin.system.config.module.domain.ModuleConfig;
import com.jyadmin.system.config.module.domain.ModuleConfigWrapper;
import com.jyadmin.system.config.module.domain.MultiModuleConfigWrapper;
import com.jyadmin.system.config.module.service.ModuleConfigService;
import com.jyadmin.system.sms.process.domain.SmsProcess;
import com.jyadmin.system.sms.process.model.dto.SmsSendDTO;
import com.jyadmin.system.sms.process.service.SmsProcessHandler;
import com.jyadmin.system.sms.process.service.SmsProcessService;
import com.jyadmin.util.RedisUtil;
import com.jyadmin.util.VerifyCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-22 23:41 <br>
 * @description: EmailProcessServiceImpl <br>
 */
@Slf4j
@Service
public class SmsProcessServiceImpl implements SmsProcessService {

    @Resource
    private ModuleConfigService moduleConfigService;

    @Resource
    private RedisUtil redisUtil;

    /**
     * 获取当前的附件配置
     * @return ConfigDetail 配置详情
     */
    private MultiModuleConfigWrapper getEnableSmsConfigDetail() {
        return moduleConfigService.getEnableMultiConfigDetail(GlobalConstants.SYS_SMS_CONFIG_ID);
    }

    @Override
    public SmsProcess sendSms(SmsSendDTO smsSendDTO) {
        // 获取当前启用的所有配置
        MultiModuleConfigWrapper multiSmsModuleConfigWrapper = getEnableSmsConfigDetail();
        ModuleConfig config = multiSmsModuleConfigWrapper.getConfig();
        List<ConfigDetail> configDetails = multiSmsModuleConfigWrapper.getConfigDetails();
        Assert.notEmpty(configDetails, "配置不能为空！");
        // 根据发送类型获取相应配置
        Optional<ConfigDetail> configDetailOptional = configDetails.stream()
                .filter(x -> x.getCode().contains(smsSendDTO.getType()))
                .findFirst();
        if (configDetailOptional.isEmpty()) Assert.notEmpty(configDetails, smsSendDTO.getType() + "配置不存在！");
        ConfigDetail configDetail = configDetailOptional.get();

        ConfigDetailJsonModel beanInfo = configDetail.getJsonObjs().stream()
                .filter(x -> "bean".equals(x.getCode())).findFirst().orElse(null);
        Assert.notNull(beanInfo, "缺少Bean配置！");

        // 获取配置相对应的处理器
        SmsProcessHandler smsProcessHandler = null;
        String storageType = config.getStorageType(); // 获取托管平台
        if ("tencent-sms".equals(storageType)) smsProcessHandler = SpringUtil.getBean(beanInfo.getValue());
        Assert.notNull(smsProcessHandler, "未找到相应处理器！");

        ModuleConfigWrapper smsConfigWrapper = new ModuleConfigWrapper()
                .setConfig(multiSmsModuleConfigWrapper.getConfig())
                .setConfigDetail(configDetail);

        return smsProcessHandler.sendSms(smsSendDTO, smsConfigWrapper);
    }

    /**
     * 短信验证码发送，body 数据结构 [用户唯一标签, 验证码, 保存分钟数]
     * @param smsSendDTO /
     * @return SmsProcess
     */
    @Override
    public SmsProcess sendVerificationCode(SmsSendDTO smsSendDTO) {
        smsSendDTO.setType(GlobalConstants.SysSmsConfigId.VERIFICATION_CODE.getCode());
        Assert.notEmpty(smsSendDTO.getBody(), "验证码唯一标签不能为空！");

        String uniqueTag = smsSendDTO.getBody()[0];
        String verifyCode = VerifyCodeUtil.generateNumberVerifyCode(GlobalConstants.SYS_SMS_VERIFICATION_CODE_LENGTH);
        String min = String.valueOf(GlobalConstants.SYS_SMS_VERIFICATION_CODE_TIME_LIMIT / 60 / 60);

        // 保存验证码信息到redis
        boolean saveStatus = redisUtil.setValue(GlobalConstants.SYS_SMS_VERIFICATION_CODE_PREFIX + ":" + uniqueTag, verifyCode, GlobalConstants.SYS_SMS_VERIFICATION_CODE_TIME_LIMIT, TimeUnit.SECONDS);
        Assert.isTrue(saveStatus, "验证码信息发送失败！");

        smsSendDTO.setBody(new String[]{ verifyCode, min });
        return this.sendSms(smsSendDTO);
    }

    @Override
    public SmsProcess sendSignInSuccess(SmsSendDTO smsSendDTO) {
        smsSendDTO.setType(GlobalConstants.SysSmsConfigId.SIGN_IN.getCode());
        return this.sendSms(smsSendDTO);
    }


}
