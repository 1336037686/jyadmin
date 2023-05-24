package com.jyadmin.system.basicset.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jyadmin.domain.Result;
import com.jyadmin.system.basicset.domain.BasicSetting;
import com.jyadmin.system.basicset.service.BasicSettingService;
import com.jyadmin.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统基础配置
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-05-08 17:00 <br>
 * @description: BasicSettingController <br>
 */
@Slf4j
@Api(value = "系统基础配置", tags = "系统：系统基础配置接口")
@RequestMapping("/api/basic-setting")
@RestController
public class BasicSettingController {

    @Resource
    private BasicSettingService basicSettingService;

    @ApiOperation(value = "更新系统基础配置", notes = "")
    @PutMapping("/update")
    @PreAuthorize("@jy.check('basic-setting:update')")
    public Result<Object> doUpdate(@RequestBody List<BasicSetting> vo) {
        return ResultUtil.toResult(basicSettingService.updateBatchById(vo));
    }

    @ApiOperation(value = "列表查询系统基础配置", notes = "")
    @GetMapping("/list")
    @PreAuthorize("@jy.check('basic-setting:list')")
    public Result<List<BasicSetting>> doQueryList() {
        List<BasicSetting> list = this.basicSettingService.list(new LambdaQueryWrapper<BasicSetting>().orderByAsc(BasicSetting::getId));
        return Result.ok(list);
    }

}
