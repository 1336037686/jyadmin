package com.jyadmin.system.job.log.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.domain.PageResult;
import com.jyadmin.system.job.log.domain.QuartzLog;
import com.jyadmin.system.job.log.model.vo.QuartzLogQueryVO;
import com.jyadmin.system.job.log.service.QuartzLogService;
import com.jyadmin.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 系统定时任务日志
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-30 20:23 <br>
 * @description: QuartzLogController <br>
 */
@Slf4j
@Api(value = "系统定时任务日志", tags = {"系统：系统定时任务日志接口"})
@RequestMapping("/api/quartz-log")
@RestController
public class QuartzLogController {

    @Resource
    private QuartzLogService quartzLogService;

    @ApiOperation(value = "分页查询系统定时任务日志信息", notes = "")
    @GetMapping("/query")
    @PreAuthorize("@jy.check('quartz-log:query')")
    public PageResult<QuartzLog> doQueryList(QuartzLogQueryVO vo) {
        return PageUtil.toPageResult(
                this.quartzLogService.page(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                new LambdaQueryWrapper<QuartzLog>()
                        .eq(Objects.nonNull(vo.getJobId()), QuartzLog::getJobId, vo.getJobId())
                        .orderByDesc(QuartzLog::getCreateTime)
        ));
    }

}
