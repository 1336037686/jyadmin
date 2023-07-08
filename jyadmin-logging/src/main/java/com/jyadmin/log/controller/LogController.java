package com.jyadmin.log.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.annotation.RateLimit;
import com.jyadmin.consts.GlobalConstants;
import com.jyadmin.consts.ResultStatus;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.log.domain.Log;
import com.jyadmin.log.model.vo.LogQueryVO;
import com.jyadmin.log.model.vo.UserLoginLog;
import com.jyadmin.log.service.LogService;
import com.jyadmin.util.DataUtil;
import com.jyadmin.util.PageUtil;
import com.jyadmin.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @RateLimit
    @ApiOperation(value = "删除日志", notes = "")
    @DeleteMapping("/remove")
    @PreAuthorize("@jy.check('log:remove')")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(logService.removeByIds(DataUtil.convertToLongForSet(ids)));
    }

    @ApiOperation(value = "根据ID获取当前日志信息", notes = "")
    @GetMapping("/query/{id}")
    @PreAuthorize("@jy.check('log:queryById')")
    public Result<Object> doQueryById(@PathVariable("id") String id) {
        return Result.ok(logService.getById(Long.parseLong(id)));
    }

    @ApiOperation(value = "分页查询日志", notes = "")
    @GetMapping("/query")
    @PreAuthorize("@jy.check('log:query')")
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
    @PreAuthorize("@jy.check('log:queryLoginPage')")
    public PageResult<UserLoginLog> doQueryLoginPage(@PathVariable("userId") String userId, LogQueryVO vo) {
        Page<Log> page = this.logService.page(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                new LambdaQueryWrapper<Log>()
                        .like(StringUtils.isNotBlank(vo.getHandleName()), Log::getHandleName, vo.getHandleName())
                        .eq(StringUtils.isNotBlank(vo.getRequestMethod()), Log::getRequestMethod, vo.getRequestMethod())
                        .eq(Objects.nonNull(vo.getExecuteStatus()), Log::getExecuteStatus, vo.getExecuteStatus())
                        .like(StringUtils.isNotBlank(vo.getUsername()), Log::getUsername, vo.getUsername())
                        .eq(true, Log::getRequestPath, GlobalConstants.SYS_LOGIN_URI)
                        .eq(true, Log::getCreateBy, Long.parseLong(userId))
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

    @ApiOperation(value = "根据用户ID分页查询用户登录日志", notes = "")
    @GetMapping("/query-login")
    @PreAuthorize("@jy.check('log:queryLogin')")
    public PageResult<UserLoginLog> doQueryLogin(LogQueryVO vo) {
        Page<Log> page = this.logService.page(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                new LambdaQueryWrapper<Log>()
                        .like(StringUtils.isNotBlank(vo.getHandleName()), Log::getHandleName, vo.getHandleName())
                        .eq(StringUtils.isNotBlank(vo.getRequestMethod()), Log::getRequestMethod, vo.getRequestMethod())
                        .eq(Objects.nonNull(vo.getExecuteStatus()), Log::getExecuteStatus, vo.getExecuteStatus())
                        .like(StringUtils.isNotBlank(vo.getUsername()), Log::getUsername, vo.getUsername())
                        .eq(true, Log::getRequestPath, GlobalConstants.SYS_LOGIN_URI)
                        .eq(true, Log::getExecuteStatus, GlobalConstants.SysExecuteStatus.SUCCESS.getValue())
                        .orderByDesc(Log::getCreateTime)
        );

        List<UserLoginLog> userLoginLogs = page.getRecords().stream().map(x -> {
            UserLoginLog loginLog = new UserLoginLog();
            BeanUtil.copyProperties(x, loginLog);
            return loginLog;
        }).collect(Collectors.toList());

        Page<UserLoginLog> userLoginLogPage = new Page<>();
        BeanUtil.copyProperties(page, userLoginLogPage);
        userLoginLogPage.setRecords(userLoginLogs);

        return PageUtil.toPageResult(userLoginLogPage);
    }

    @ApiOperation(value = "根据用户ID分页查询用户行为日志", notes = "")
    @GetMapping("/query-active")
    @PreAuthorize("@jy.check('log:queryActive')")
    public PageResult<Log> doQueryActive(LogQueryVO vo) {
        return PageUtil.toPageResult(this.logService.page(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                new LambdaQueryWrapper<Log>()
                        .like(StringUtils.isNotBlank(vo.getHandleName()), Log::getHandleName, vo.getHandleName())
                        .eq(StringUtils.isNotBlank(vo.getRequestMethod()), Log::getRequestMethod, vo.getRequestMethod())
                        .eq(Objects.nonNull(vo.getExecuteStatus()), Log::getExecuteStatus, vo.getExecuteStatus())
                        .like(StringUtils.isNotBlank(vo.getUsername()), Log::getUsername, vo.getUsername())
                        .ne(true, Log::getRequestPath, GlobalConstants.SYS_LOGIN_URI)
                        .orderByDesc(Log::getCreateTime)
        ));
    }

}
