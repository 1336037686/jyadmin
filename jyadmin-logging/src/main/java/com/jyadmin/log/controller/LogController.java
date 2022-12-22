package com.jyadmin.log.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.consts.ResultStatus;
import com.jyadmin.domain.BasePageVO;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.log.domain.Log;
import com.jyadmin.log.model.vo.LogQueryVO;
import com.jyadmin.log.model.vo.UserLoginLog;
import com.jyadmin.log.service.LogService;
import com.jyadmin.util.PageUtil;
import com.jyadmin.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
                                .orderByDesc(Log::getCreateTime)
                )
        );
    }

    @ApiOperation(value = "根据用户ID分页查询用户登录日志", notes = "")
    @GetMapping("/query-user-login/{userId}")
    public PageResult<UserLoginLog> doQueryLoginPage(@PathVariable("userId") String userId, BasePageVO vo) {
        Page<Log> page = this.logService.page(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                new LambdaQueryWrapper<Log>()
                        .eq(true, Log::getRequestPath, "/api/auth/login")
                        .eq(true, Log::getCreateBy, userId)
                        .orderByDesc(Log::getCreateTime)
        );

        List<UserLoginLog> userLoginLogs = page.getRecords().stream().map(x -> {
            UserLoginLog loginLog = new UserLoginLog();
            BeanUtil.copyProperties(x, loginLog);
            return loginLog;
        }).collect(Collectors.toList());

        return new PageResult<UserLoginLog>()
                .setStatus(ResultStatus.SUCCESS)
                .setSuccess(true)
                .setPageNumber(page.getCurrent())
                .setPageSize(page.getSize())
                .setPages(page.getPages())
                .setTotal(page.getTotal())
                .setRecords(userLoginLogs)
                .setHasPrevious(page.hasPrevious())
                .setHasNext(page.hasNext());
    }

}
