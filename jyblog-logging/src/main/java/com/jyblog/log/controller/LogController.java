package com.jyblog.log.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyblog.domain.PageResult;
import com.jyblog.domain.Result;
import com.jyblog.log.domain.Log;
import com.jyblog.log.model.vo.LogQueryVO;
import com.jyblog.log.service.LogService;
import com.jyblog.util.PageUtil;
import com.jyblog.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Set;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-30 01:23 <br>
 * @description: LogController <br>
 */
@Slf4j
@Api(value = "系统操作日志", tags = {"系统：操作日志"})
@RequestMapping("/api/log")
@RestController
public class LogController {

    @Resource
    private LogService logService;

    @ApiOperation(value = "删除日志", notes = "")
    @DeleteMapping("/remove")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(logService.removeByIds(ids));
    }

    @ApiOperation(value = "根据ID获取当前日志信息", notes = "")
    @GetMapping("/query/{id}")
    public Result<Object> doQueryById(@PathVariable String id) {
        return Result.ok(logService.getById(id));
    }

    @ApiOperation(value = "分页查询日志", notes = "")
    @GetMapping("/query")
    public PageResult<Log> doQueryPage(LogQueryVO vo) {
        return PageUtil.toPageResult(
                this.logService.page(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                        new LambdaQueryWrapper<Log>()
                                .like(StringUtils.isNotBlank(vo.getHandleName()), Log::getHandleName, vo.getHandleName())
                                .eq(StringUtils.isNotBlank(vo.getRequestMethod()), Log::getRequestMethod, vo.getRequestMethod())
                                .eq(Objects.nonNull(vo.getExecuteStatus()), Log::getExecuteStatus, vo.getExecuteStatus())
                                .like(StringUtils.isNotBlank(vo.getUsername()), Log::getUsername, vo.getUsername())
                )
        );
    }

}
