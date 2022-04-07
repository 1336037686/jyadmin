package com.jyblog.system.datadict.controller;

import cn.hutool.core.bean.BeanUtil;
import com.jyblog.domain.Result;
import com.jyblog.system.datadict.domain.SysDataDict;
import com.jyblog.system.datadict.model.vo.SysDataDictRootVO;
import com.jyblog.system.datadict.service.SysDataDictService;
import com.jyblog.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 数据字典
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-07 21:41 <br>
 * @description: SysDataDictController <br>
 */
@Slf4j
@Api(value = "数据字典", tags = {"数据字典接口"})
@RequestMapping("system/data-dict")
@RestController
public class SysDataDictController {

    @Resource
    private SysDataDictService sysDataDictService;

    /**
     * 创建根节点
     * @param rootVO
     * @return
     */
    @ApiOperation(value = "创建根节点", notes = "")
    @PostMapping("/create-root")
    public Result<Object> doCreateRootDataDict(@RequestBody @Valid SysDataDictRootVO rootVO) {
        boolean save = sysDataDictService.save(BeanUtil.copyProperties(rootVO, SysDataDict.class));
        return ResultUtil.toResult(save);
    }


    // TODO: 创建子节点

    // TODO: 更新字典

    // TODO: 查询字典


}
