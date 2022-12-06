/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.jyadmin.system.job.common;

import com.jyadmin.consts.GlobalConstants;
import com.jyadmin.consts.ResultStatus;
import com.jyadmin.exception.ApiException;
import com.jyadmin.system.job.manage.domain.QuartzJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

import static org.quartz.TriggerBuilder.newTrigger;

/**
 * 基础任务
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-30 11:03 <br>
 * @description: QuartzManage <br>
 */
@Slf4j
@Component
public class QuartzManage {

    private static final String JOB_NAME = "SYS_TASK_";

    @Resource
    private Scheduler scheduler;

    /**
     * 添加job
     * @param quartzJob 系统定时任务
     */
    public void addJob(QuartzJob quartzJob){
        log.debug("添加 {} {} job", quartzJob.getCode(), quartzJob.getName());
        try {
            // 构建job信息
            JobDetail jobDetail = JobBuilder
                    .newJob(ActiveJob.class)
                    .withIdentity(JOB_NAME + quartzJob.getId()).build();

            //通过触发器名和cron 表达式创建 Trigger
            Trigger cronTrigger = newTrigger()
                    .withIdentity(JOB_NAME + quartzJob.getId())
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule(quartzJob.getCronExpression()))
                    .build();
            JobDataMap jobDataMap = cronTrigger.getJobDataMap();
            jobDataMap.put(QuartzJob.JOB_KEY, quartzJob);
            this.setSecurityContext(jobDataMap);
            //重置启动时间
            ((CronTriggerImpl)cronTrigger).setStartTime(new Date());
            //执行定时任务
            scheduler.scheduleJob(jobDetail,cronTrigger);
            // 暂停任务
            if (Objects.equals(0, quartzJob.getJobStatus())) {
                pauseJob(quartzJob);
            }
        } catch (Exception e){
            log.error("系统定时任务创建失败", e);
            throw new ApiException(ResultStatus.FAIL, "系统定时任务创建失败");
        }
    }

    /**
     * 更新job cron表达式
     * @param quartzJob 系统定时任务
     */
    public void updateJobCron(QuartzJob quartzJob){
        log.debug("更新 {} {} job", quartzJob.getCode(), quartzJob.getName());
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(JOB_NAME + quartzJob.getId());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 如果不存在则创建一个定时任务
            if(trigger == null){
                addJob(quartzJob);
                trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            }
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzJob.getCronExpression());
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            //重置启动时间
            ((CronTriggerImpl)trigger).setStartTime(new Date());

            JobDataMap jobDataMap = trigger.getJobDataMap();
            jobDataMap.put(QuartzJob.JOB_KEY,quartzJob);
            this.setSecurityContext(jobDataMap);

            scheduler.rescheduleJob(triggerKey, trigger);
            // 暂停任务
            if (Objects.equals(0, quartzJob.getJobStatus())) {
                pauseJob(quartzJob);
            }
        } catch (Exception e){
            log.error("系统定时任务更新失败", e);
            throw new ApiException(ResultStatus.FAIL, "系统定时任务更新失败");
        }

    }

    /**
     * 删除一个job
     * @param quartzJob 系统定时任务
     */
    public void deleteJob(QuartzJob quartzJob){
        log.debug("删除 {} {} job", quartzJob.getCode(), quartzJob.getName());
        try {
            JobKey jobKey = JobKey.jobKey(JOB_NAME + quartzJob.getId());
            // 暂停任务
            scheduler.pauseJob(jobKey);
            // 删除任务
            scheduler.deleteJob(jobKey);
        } catch (Exception e){
            log.error("删除定时任务失败", e);
            throw new ApiException(ResultStatus.FAIL, "删除定时任务失败");
        }
    }

    /**
     * 恢复一个job
     * @param quartzJob 系统定时任务
     */
    public void resumeJob(QuartzJob quartzJob){
        log.debug("恢复 {} {} job", quartzJob.getCode(), quartzJob.getName());
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(JOB_NAME + quartzJob.getId());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 如果不存在则创建一个定时任务
            if(trigger == null) {
                addJob(quartzJob);
                trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            }
            this.setSecurityContext(trigger.getJobDataMap());
            JobKey jobKey = JobKey.jobKey(JOB_NAME + quartzJob.getId());
            // 恢复任务
            scheduler.resumeJob(jobKey);
        } catch (Exception e){
            log.error("恢复定时任务失败", e);
            throw new ApiException(ResultStatus.FAIL, "恢复定时任务失败");
        }
    }

    /**
     * 立即执行job
     * @param quartzJob 系统定时任务
     */
    public void runJobNow(QuartzJob quartzJob){
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(JOB_NAME + quartzJob.getId());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 如果不存在则创建一个定时任务
            if(trigger == null) {
                addJob(quartzJob);
            }

            JobDataMap dataMap = new JobDataMap();
            dataMap.put(QuartzJob.JOB_KEY, quartzJob);
            this.setSecurityContext(dataMap);

            JobKey jobKey = JobKey.jobKey(JOB_NAME + quartzJob.getId());
            scheduler.triggerJob(jobKey, dataMap);
        } catch (Exception e){
            log.error("定时任务执行失败", e);
            throw new ApiException(ResultStatus.FAIL, "定时任务执行失败");
        }
    }

    /**
     * 暂停一个job
     * @param quartzJob 系统定时任务
     */
    public void pauseJob(QuartzJob quartzJob){
        log.debug("暂停 {} {} job", quartzJob.getCode(), quartzJob.getName());
        try {
            JobKey jobKey = JobKey.jobKey(JOB_NAME + quartzJob.getId());
            scheduler.pauseJob(jobKey);
        } catch (Exception e){
            log.error("定时任务暂停失败", e);
            throw new ApiException(ResultStatus.FAIL, "系统定时任务暂停失败");
        }
    }

    /**
     * 准备定时任务初始化数据失败
     * @param jobDataMap jobDataMap
     */
    public void setSecurityContext (JobDataMap jobDataMap) {
        try {
            SecurityContext context = SecurityContextHolder.getContext();
            if (!jobDataMap.containsKey(GlobalConstants.SYS_SECURITY_CONTEXT)) {
                jobDataMap.put(GlobalConstants.SYS_SECURITY_CONTEXT, context);
            }
        } catch (Exception e) {
            log.error("准备定时任务初始化数据失败", e);
            throw new ApiException(ResultStatus.FAIL, "准备定时任务初始化数据失败");
        }
    }
}
