package com.jyadmin.system.sms.config.controller;

import cn.hutool.core.bean.BeanUtil;
import com.jyadmin.annotation.RateLimit;
import com.jyadmin.consts.GlobalConstants;
import com.jyadmin.domain.Result;
import com.jyadmin.log.annotation.Log;
import com.jyadmin.system.config.detail.domain.ConfigDetail;
import com.jyadmin.system.config.module.domain.ModuleConfig;
import com.jyadmin.system.config.module.service.ModuleConfigService;
import com.jyadmin.system.sms.config.model.vo.SmsConfigUpdateVO;
import com.jyadmin.system.sms.config.service.SmsConfigService;
import com.jyadmin.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-23 23:10 <br>
 * @description: EmailConfigController <br>
 */
@Slf4j
@Api(value = "系统短信配置", tags = {"系统：系统短信配置接口"})
@RequestMapping("/api/sms-config")
@RestController
public class SmsConfigController {

    @Resource
    private SmsConfigService smsConfigService;
    @Resource
    private ModuleConfigService moduleConfigService;

    @RateLimit
    @Log(title = "系统短信配置：更新短信配置", desc = "更新短信配置")
    @ApiOperation(value = "更新短信配置", notes = "")
    @PutMapping("/update")
    @PreAuthorize("@jy.check('sms-config:update')")
    public Result<Object> doUpdate(@RequestBody @Valid SmsConfigUpdateVO vo) {
        ModuleConfig fileConfig = moduleConfigService.getById(GlobalConstants.SYS_SMS_CONFIG_ID);
        BeanUtil.copyProperties(vo, fileConfig);
        return ResultUtil.toResult(moduleConfigService.updateById(fileConfig));
    }

    @ApiOperation(value = "查找系统短信配置", notes = "")
    @GetMapping("/query")
    @PreAuthorize("@jy.check('sms-config:query')")
    public Result<Object> doQueryById() {
        return Result.ok(moduleConfigService.getById(GlobalConstants.SYS_SMS_CONFIG_ID));
    }

    @ApiOperation(value = "查找系统模板配置列表", notes = "")
    @GetMapping("/query-config/{code}")
    @PreAuthorize("@jy.check('sms-config:queryConfig')")
    public Result<List<ConfigDetail>> doQueryConfigListByCode(@PathVariable("code") String code) {
        List<ConfigDetail> configDetails = this.smsConfigService.getConfigListByCode(code);
        return Result.ok(configDetails);
    }

}
