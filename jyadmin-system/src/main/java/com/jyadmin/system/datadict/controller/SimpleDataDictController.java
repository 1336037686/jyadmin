package com.jyadmin.system.datadict.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.annotation.RateLimit;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.log.annotation.Log;
import com.jyadmin.system.datadict.domain.SimpleDataDict;
import com.jyadmin.system.datadict.model.vo.SimpleDataDictCreateVO;
import com.jyadmin.system.datadict.model.vo.SimpleDataDictQueryVO;
import com.jyadmin.system.datadict.model.vo.SimpleDataDictUpdateVO;
import com.jyadmin.system.datadict.service.SimpleDataDictService;
import com.jyadmin.util.DataUtil;
import com.jyadmin.util.PageUtil;
import com.jyadmin.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 通用数据字典
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-10-30 16:11 <br>
 * @description: SimpleDataDictController <br>
 */
@Slf4j
@Api(value = "通用数据字典", tags = {"系统：通用数据字典接口"})
@RequestMapping("/api/simple-data-dict")
@RestController
public class SimpleDataDictController {

    @Resource
    private SimpleDataDictService simpleDataDictService;

    @RateLimit
    @Log(title = "系统管理：新增通用数据字典", desc = "新增通用数据字典")
    @ApiOperation(value = "新增通用数据字典", notes = "")
    @PostMapping("/create")
    @PreAuthorize("@jy.check('simple-datadict:create')")
    public Result<Object> doCreate(@RequestBody @Valid SimpleDataDictCreateVO vo) {
        return ResultUtil.toResult(simpleDataDictService.save(BeanUtil.copyProperties(vo, SimpleDataDict.class)));
    }

    @RateLimit
    @Log(title = "系统管理：更新通用数据字典", desc = "更新通用数据字典")
    @ApiOperation(value = "更新通用数据字典", notes = "")
    @PutMapping("/update")
    @PreAuthorize("@jy.check('simple-datadict:update')")
    public Result<Object> doUpdate(@RequestBody @Valid SimpleDataDictUpdateVO vo) {
        SimpleDataDict simpleDataDict = simpleDataDictService.getById(vo.getId());
        BeanUtil.copyProperties(vo, simpleDataDict);
        return ResultUtil.toResult(simpleDataDictService.updateById(simpleDataDict));
    }

    @RateLimit
    @Log(title = "系统管理：删除通用数据字典", desc = "删除通用数据字典")
    @ApiOperation(value = "删除通用数据字典", notes = "")
    @DeleteMapping("/remove")
    @PreAuthorize("@jy.check('simple-datadict:remove')")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(simpleDataDictService.removeByIds(DataUtil.convertToLongForSet(ids)));
    }

    @ApiOperation(value = "根据ID查找通用数据字典信息", notes = "")
    @GetMapping("/query/{id}")
    @PreAuthorize("@jy.check('simple-datadict:queryById')")
    public Result<Object> doQueryById(@PathVariable String id) {
        return Result.ok(simpleDataDictService.getById(Long.parseLong(id)));
    }

    @ApiOperation(value = "分页查询通用数据字典信息", notes = "")
    @GetMapping("/query")
    @PreAuthorize("@jy.check('simple-datadict:query')")
    public PageResult<SimpleDataDict> doQueryPage(SimpleDataDictQueryVO vo) {
        return PageUtil.toPageResult(
                this.simpleDataDictService.page(
                        new Page<>(vo.getPageNumber(), vo.getPageSize()),
                        new LambdaQueryWrapper<SimpleDataDict>()
                                .like(StringUtils.isNotBlank(vo.getName()), SimpleDataDict::getName, vo.getName())
                                .like(StringUtils.isNotBlank(vo.getCode()), SimpleDataDict::getCode, vo.getCode())
                                .orderByDesc(SimpleDataDict::getCreateTime)
                )
        );
    }

    @ApiOperation(value = "列表查询通用数据字典信息", notes = "")
    @GetMapping("/list")
    @PreAuthorize("@jy.check('simple-datadict:list')")
    public Result<List<SimpleDataDict>> doQueryList(SimpleDataDictQueryVO vo) {
        List<SimpleDataDict> list = this.simpleDataDictService.list(
                new LambdaQueryWrapper<SimpleDataDict>()
                        .like(StringUtils.isNotBlank(vo.getName()), SimpleDataDict::getName, vo.getName())
                        .like(StringUtils.isNotBlank(vo.getCode()), SimpleDataDict::getCode, vo.getCode())
                        .orderByDesc(SimpleDataDict::getCreateTime)
        );
        return Result.ok(list);
    }

}
