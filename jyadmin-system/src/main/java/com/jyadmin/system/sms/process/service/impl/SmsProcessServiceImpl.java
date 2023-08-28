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
import java.util.ArrayList;
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
    private ModuleConfigWrapper getEnableSmsConfigDetail() {
        return moduleConfigService.getEnableConfigDetail(GlobalConstants.SYS_SMS_CONFIG_ID);
    }

    @Override
    public SmsProcess sendSms(SmsSendDTO smsSendDTO) {
        // 获取当前启用的所有配置
        ModuleConfigWrapper smsModuleConfigWrapper = getEnableSmsConfigDetail();
        ModuleConfig config = smsModuleConfigWrapper.getConfig();
        ConfigDetail configDetail = smsModuleConfigWrapper.getConfigDetail();
        Assert.notNull(configDetail, "配置不能为空！");

        ConfigDetailJsonModel beanInfo = configDetail.getJsonObjs().stream()
                .filter(x -> "bean".equals(x.getCode())).findFirst().orElse(null);
        Assert.notNull(beanInfo, "缺少Bean配置！");

        // 获取配置相对应的处理器
        SmsProcessHandler smsProcessHandler = null;
        String storageType = config.getStorageType(); // 获取托管平台
        if ("tencent-sms".equals(storageType)) smsProcessHandler = SpringUtil.getBean(beanInfo.getValue());
        if ("uni-sms".equals(storageType)) smsProcessHandler = SpringUtil.getBean(beanInfo.getValue());
        Assert.notNull(smsProcessHandler, "未找到相应处理器！");

        return smsProcessHandler.sendSms(smsSendDTO, smsModuleConfigWrapper);
    }

    /**
     * 短信验证码发送
     * @param smsSendDTO /
     * @return SmsProcess
     */
    @Override
    public SmsProcess sendVerificationCode(SmsSendDTO smsSendDTO) {
        smsSendDTO.setType(GlobalConstants.SysSmsTemplate.VERIFICATION_CODE.getCode());
        Assert.notNull(smsSendDTO.getUniqueId(), "验证码唯一标签不能为空！");

        String uniqueId = smsSendDTO.getUniqueId();
        String code = VerifyCodeUtil.generateNumberVerifyCode(GlobalConstants.SYS_SMS_VERIFICATION_CODE_LENGTH); // 验证码
        String ttl = String.valueOf(GlobalConstants.SYS_SMS_VERIFICATION_CODE_TIME_LIMIT / 60); // 有效时间 min

        List<SmsSendDTO.SmsBody> bodyList = new ArrayList<>();
        bodyList.add(new SmsSendDTO.SmsBody().setKey("code").setValue(code));
        bodyList.add(new SmsSendDTO.SmsBody().setKey("ttl").setValue(ttl));
        smsSendDTO.setBody(bodyList);
        SmsProcess smsProcess = this.sendSms(smsSendDTO);

        if (smsProcess.getSuccess()) {
            // 保存验证码信息到redis
            boolean saveStatus = redisUtil.setValue(GlobalConstants.SYS_SMS_VERIFICATION_CODE_PREFIX + ":" + uniqueId, code, GlobalConstants.SYS_SMS_VERIFICATION_CODE_TIME_LIMIT, TimeUnit.SECONDS);
            Assert.isTrue(saveStatus, "验证码信息发送失败！");
        }
        return smsProcess;
    }


}
