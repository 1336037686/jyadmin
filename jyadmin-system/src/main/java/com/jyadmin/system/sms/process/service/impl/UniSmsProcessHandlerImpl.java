package com.jyadmin.system.sms.process.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apistd.uni.Uni;
import com.apistd.uni.UniException;
import com.apistd.uni.UniResponse;
import com.apistd.uni.sms.UniMessage;
import com.apistd.uni.sms.UniSMS;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.jyadmin.consts.ResultStatus;
import com.jyadmin.exception.ApiException;
import com.jyadmin.system.config.detail.domain.ConfigDetail;
import com.jyadmin.system.config.detail.service.ConfigDetailService;
import com.jyadmin.system.config.module.domain.ModuleConfigWrapper;
import com.jyadmin.system.sms.process.domain.SmsProcess;
import com.jyadmin.system.sms.process.model.dto.SmsSendDTO;
import com.jyadmin.system.sms.process.service.SmsProcessHandler;
import com.jyadmin.system.sms.record.domain.SmsRecord;
import com.jyadmin.system.sms.record.service.SmsRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-08-27 22:54 <br>
 * @description: UniSmsProcessHandlerImpl <br>
 */
@Slf4j
@Service("uniSmsProcessHandler")
public class UniSmsProcessHandlerImpl implements SmsProcessHandler {

    /**
     * 成功响应码
     */
    private String SUCCESS_CODE = "0";

    @Resource
    private SmsRecordService smsRecordService;

    @Resource
    private ConfigDetailService configDetailService;

    @Override
    public SmsProcess sendSms(SmsSendDTO smsSendDTO, ModuleConfigWrapper smsConfigWrapper) {
        log.info("unisms 短信发送: {}", JSON.toJSONString(smsSendDTO));
        ConfigDetail configDetail = smsConfigWrapper.getConfigDetail();
        String accessKeyId = configDetailService.getValueByCode(configDetail, "accessKeyId");
        String accessKeySecret = configDetailService.getValueByCode(configDetail, "accessKeySecret");
        String signature = configDetailService.getValueByCode(configDetail, "signature");
        String templateMap = configDetailService.getValueByCode(configDetail, "templateMap");

        String templateId = null;
        String templateContent = null;
        JSONArray templateMapJsonArray = JSON.parseArray(templateMap);
        for (int i = 0; i < templateMapJsonArray.size(); i++) {
            JSONObject jsonObject = templateMapJsonArray.getJSONObject(i);
            if (smsSendDTO.getType().equals(jsonObject.get("type"))) {
                templateId = jsonObject.get("templateId").toString();
                templateContent = jsonObject.get("templateContent").toString();
                break;
            }
        }

        Assert.notNull(templateId, "配置信息异常，未配置相应模板ID");
        Assert.notNull(templateContent, "配置信息异常，未配置相应模板内容");

        // 初始化
        Uni.init(accessKeyId, accessKeySecret); // 若使用简易验签模式仅传入第一个参数即可

        // 设置自定义参数 (变量短信)
        Map<String, String> templateData = Maps.newHashMap();
        for (SmsSendDTO.SmsBody smsBody : smsSendDTO.getBody()) templateData.put(smsBody.getKey(), smsBody.getValue());

        // 替换模板内容
        for (Map.Entry<String, String> entry : templateData.entrySet()) {
            templateContent = templateContent.replaceAll("\\{" + entry.getKey() + "\\}", entry.getValue());
        }

        // 构建信息
        UniMessage message = UniSMS.buildMessage()
                .setTo(smsSendDTO.getReceiver())
                .setSignature(signature)
                .setTemplateId(templateId)
                .setTemplateData(templateData);

        // 发送短信
        try {
            UniResponse res = message.send();
            log.info("unisms 短信发送结果: {}", JSON.toJSONString(res));

            // 发送失败
            if (!SUCCESS_CODE.equals(res.code)) return new SmsProcess().setSuccess(false).setMessage(res.message).setId(null);

            // 保存短信发送记录
            SmsRecord record = new SmsRecord();
            record.setContent(templateContent);
            record.setPhone(smsSendDTO.getReceiver());
            record.setSource(smsConfigWrapper.getConfig().getStorageType());
            record.setRelevance(smsSendDTO.getRelevance());
            log.info("sms record: {}", record);
            smsRecordService.save(record);
            return new SmsProcess().setSuccess(true).setMessage(res.message).setId(record.getId());
        } catch (UniException e) {
            log.error("unisms 短信发送异常, {}", e.getMessage());
            throw new ApiException(ResultStatus.SMS_SEND_FAIL);
        }
    }

}
