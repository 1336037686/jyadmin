package com.jyadmin.system.job.manage.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.annotation.RateLimit;
import com.jyadmin.consts.ResultStatus;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.exception.ApiException;
import com.jyadmin.log.annotation.Log;
import com.jyadmin.system.job.manage.domain.QuartzJob;
import com.jyadmin.system.job.manage.model.vo.QuartzJobCreateVO;
import com.jyadmin.system.job.manage.model.vo.QuartzJobQueryVO;
import com.jyadmin.system.job.manage.model.vo.QuartzJobStatusUpdateVO;
import com.jyadmin.system.job.manage.model.vo.QuartzJobUpdateVO;
import com.jyadmin.system.job.manage.service.QuartzJobService;
import com.jyadmin.util.DataUtil;
import com.jyadmin.util.PageUtil;
import com.jyadmin.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统定时任务管理
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-30 20:23 <br>
 * @description: QuartzJobController <br>
 */
@Slf4j
@Api(value = "系统定时任务管理", tags = {"系统：系统定时任务管理接口"})
@RequestMapping("/api/quartz-job")
@RestController
public class QuartzJobController {

    @Resource
    private QuartzJobService quartzService;

    @RateLimit
    @Log(title = "系统定时任务管理：新增系统定时任务", desc = "新增系统定时任务")
    @ApiOperation(value = "新增系统定时任务", notes = "")
    @PostMapping("/create")
    @PreAuthorize("@jy.check('quartz-job:create')")
    public Result<Object> doCreate(@RequestBody @Valid QuartzJobCreateVO vo) {
        if (!CronExpression.isValidExpression(vo.getCronExpression())) throw new ApiException(ResultStatus.FAIL, "cron表达式格式异常");
        QuartzJob quartzJob = BeanUtil.copyProperties(vo, QuartzJob.class);
        quartzJob.setJobStatus(0);
        quartzJob.setRunStatus(0);
        return ResultUtil.toResult(quartzService.saveJob(quartzJob));
    }

    @RateLimit
    @Log(title = "系统定时任务管理：更新系统定时任务", desc = "更新系统定时任务")
    @ApiOperation(value = "更新系统定时任务", notes = "")
    @PutMapping("/update")
    @PreAuthorize("@jy.check('quartz-job:update')")
    public Result<Object> doUpdate(@RequestBody @Valid QuartzJobUpdateVO vo) {
        if (!CronExpression.isValidExpression(vo.getCronExpression())) throw new ApiException(ResultStatus.FAIL, "cron表达式格式异常");
        QuartzJob quartzJob = quartzService.getById(vo.getId());
        if (Objects.equals(1, quartzJob.getJobStatus())) throw new ApiException(ResultStatus.FAIL, "请先暂停任务再进行更新");
        BeanUtil.copyProperties(vo, quartzJob);
        return ResultUtil.toResult(quartzService.updateJob(quartzJob));
    }

    @RateLimit
    @Log(title = "系统定时任务管理：更新系统定时任务任务状态", desc = "更新系统定时任务任务状态")
    @ApiOperation(value = "更新系统定时任务任务状态", notes = "")
    @PutMapping("/update-status")
    @PreAuthorize("@jy.check('quartz-job:updateStatus')")
    public Result<Object> doUpdateJobStatus(@RequestBody @Valid QuartzJobStatusUpdateVO vo) {
        QuartzJob quartzJob = quartzService.getById(vo.getId());
        BeanUtil.copyProperties(vo, quartzJob);
        return ResultUtil.toResult(quartzService.updateJobStatus(quartzJob));
    }

    @RateLimit
    @Log(title = "系统定时任务管理：删除系统定时任务", desc = "删除系统定时任务")
    @ApiOperation(value = "删除系统定时任务", notes = "")
    @DeleteMapping("/remove")
    @PreAuthorize("@jy.check('quartz-job:remove')")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(quartzService.removeJobs(DataUtil.convertToLongForSet(ids)));
    }

    @ApiOperation(value = "根据ID查找系统定时任务信息", notes = "")
    @GetMapping("/query/{id}")
    @PreAuthorize("@jy.check('quartz-job:queryById')")
    public Result<Object> doQueryById(@PathVariable String id) {
        return Result.ok(quartzService.getById(Long.parseLong(id)));
    }

    @ApiOperation(value = "列表查询系统定时任务信息", notes = "")
    @GetMapping("/list")
    @PreAuthorize("@jy.check('quartz-job:list')")
    public Result<List<QuartzJob>> doQueryList(QuartzJobQueryVO vo) {
        return Result.ok(this.quartzService.getBaseMapper().selectList(
                new LambdaQueryWrapper<QuartzJob>()
                        .like(Objects.nonNull(vo.getCode()), QuartzJob::getCode, vo.getCode())
                        .like(Objects.nonNull(vo.getName()), QuartzJob::getName, vo.getName())
                        .like(Objects.nonNull(vo.getPrincipal()), QuartzJob::getPrincipal, vo.getPrincipal())
                        .orderByDesc(QuartzJob::getCreateTime)
        ));
    }

    @ApiOperation(value = "分页查询系统定时任务信息", notes = "")
    @GetMapping("/query")
    @PreAuthorize("@jy.check('quartz-job:query')")
    public PageResult<QuartzJob> doQueryPage(QuartzJobQueryVO vo) {
        return PageUtil.toPageResult(
                this.quartzService.page(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                        new LambdaQueryWrapper<QuartzJob>()
                                .like(Objects.nonNull(vo.getCode()), QuartzJob::getCode, vo.getCode())
                                .like(Objects.nonNull(vo.getName()), QuartzJob::getName, vo.getName())
                                .like(Objects.nonNull(vo.getPrincipal()), QuartzJob::getPrincipal, vo.getPrincipal())
                                .orderByDesc(QuartzJob::getCreateTime)
                )
        );
    }

}
