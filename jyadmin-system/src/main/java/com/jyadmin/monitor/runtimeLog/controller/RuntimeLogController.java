package com.jyadmin.monitor.runtimeLog.controller;

import com.jyadmin.domain.Result;
import com.jyadmin.monitor.runtimeLog.model.vo.RuntimeLogReqVO;
import com.jyadmin.monitor.runtimeLog.model.vo.RuntimeLogDirResVO;
import com.jyadmin.monitor.runtimeLog.service.RuntimeLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 运行日志
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-06-04 01:18 <br>
 * @description: RunTimeLogController <br>
 */
@Slf4j
@Api(value = "运行日志", tags = {"监控：运行日志接口"})
@RequestMapping("/api/runtimeLog")
@RestController
public class RuntimeLogController {

    @Resource
    private RuntimeLogService runtimeLogService;

    @ApiOperation(value = "获取运行日志目录", notes = "")
    @GetMapping("/query")
    // @PreAuthorize("@jy.check('runtimeLog:query')")
    public Result<List<RuntimeLogDirResVO>> doQueryRuntimeLogDir() {
        return Result.ok(runtimeLogService.getRuntimeLogDir());
    }


    @ApiOperation(value = "获取运行日志内容", notes = "")
    @PostMapping("/query-log")
    public Result<String> doQueryRuntimeLogContent(@RequestBody @Valid RuntimeLogReqVO runtimeLogVO) {
        return Result.ok(runtimeLogService.getRuntimeLogContent(runtimeLogVO));
    }

}
