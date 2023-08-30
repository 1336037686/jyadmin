package com.jyadmin.system.sms.process.controller;

import cn.hutool.core.bean.BeanUtil;
import com.jyadmin.annotation.RateLimit;
import com.jyadmin.domain.Result;
import com.jyadmin.log.annotation.Log;
import com.jyadmin.system.sms.process.domain.SmsProcess;
import com.jyadmin.system.sms.process.model.dto.SmsSendDTO;
import com.jyadmin.system.sms.process.model.vo.SmsSendVO;
import com.jyadmin.system.sms.process.service.SmsProcessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-22 23:26 <br>
 * @description: EmailProcessController <br>
 */
@Slf4j
@Api(value = "系统短信处理", tags = {"系统：系统短信处理接口"})
@RequestMapping("/api/sms-process")
@RestController
public class SmsProcessController {

    @Resource
    private SmsProcessService smsProcessService;


    @RateLimit(period = 60, count = 1)
    @Log(title = "系统短信处理：验证码发送", desc = "验证码发送")
    @ApiOperation(value = "验证码发送", notes = "")
    @PostMapping("/send/verificationCode")
    public Result<Object> doSendVerificationCode(@RequestBody @Valid SmsSendVO vo) {
        SmsSendDTO smsSendDTO = new SmsSendDTO();
        BeanUtil.copyProperties(vo, smsSendDTO);
        SmsProcess smsProcess = smsProcessService.sendVerificationCode(smsSendDTO);
        return Result.ok(smsProcess);
    }


}
