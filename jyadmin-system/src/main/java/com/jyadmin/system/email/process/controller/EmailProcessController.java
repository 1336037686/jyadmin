package com.jyadmin.system.email.process.controller;

import cn.hutool.core.bean.BeanUtil;
import com.jyadmin.domain.Result;
import com.jyadmin.log.annotation.Log;
import com.jyadmin.system.email.process.domain.EmailProcess;
import com.jyadmin.system.email.process.model.dto.EmailSendDTO;
import com.jyadmin.system.email.process.model.vo.EmailSendVO;
import com.jyadmin.system.email.process.service.EmailProcessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
@Api(value = "邮件处理", tags = {"系统：邮件处理接口"})
@RequestMapping("/api/email-process")
@RestController
public class EmailProcessController {

    @Resource
    private EmailProcessService emailProcessService;

    @Log(title = "邮件发送", desc = "邮件发送")
    @ApiOperation(value = "邮件发送", notes = "")
    @PostMapping("/send")
    public Result<Object> doSend(@RequestBody @Valid EmailSendVO vo) {
        EmailSendDTO emailSendDTO = new EmailSendDTO();
        BeanUtil.copyProperties(vo, emailSendDTO);
        EmailProcess emailProcess = emailProcessService.sendEmail(emailSendDTO);
        return Result.ok(emailProcess);
    }



}
