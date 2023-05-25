package com.jyadmin.system.job.common;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import com.jyadmin.consts.GlobalConstants;
import com.jyadmin.system.email.process.domain.EmailProcess;
import com.jyadmin.system.email.process.model.dto.EmailSendDTO;
import com.jyadmin.system.email.process.service.EmailProcessService;
import com.jyadmin.system.job.log.domain.QuartzLog;
import com.jyadmin.system.job.log.service.QuartzLogService;
import com.jyadmin.system.job.manage.domain.QuartzJob;
import com.jyadmin.system.job.manage.service.QuartzJobService;
import com.jyadmin.util.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;
import java.util.Objects;

/**
 * 基础任务
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-30 11:03 <br>
 * @description: ActiveJob <br>
 */
@DisallowConcurrentExecution
@Slf4j
public class ActiveJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) {
        // 设置SecurityContext，解决Job内无法获取登录用户问题
        SecurityContext securityContext = (SecurityContext) context.getMergedJobDataMap().get(GlobalConstants.SYS_SECURITY_CONTEXT);
        SecurityContextHolder.setContext(securityContext);

        QuartzJobService quartzJobService = SpringUtil.getBean(QuartzJobService.class);
        QuartzLogService quartzLogService = SpringUtil.getBean(QuartzLogService.class);

        log.debug("=========== ActiveJob Run ==========");
        // 获取任务
        QuartzJob quartzJob = (QuartzJob) context.getMergedJobDataMap().get(QuartzJob.JOB_KEY);
        log.debug("ActiveJob job entity: {}", JSON.toJSONString(quartzJob));
        QuartzLog quartzLog = new QuartzLog();
        BeanUtil.copyProperties(quartzJob, quartzLog, "id");
        quartzLog.setJobId(quartzJob.getId());
        long startTime = System.currentTimeMillis() ;
        // 执行任务
        try {
            // 构造任务执行器，会将params转换为json
            JobRunningEntity jobRunningEntity = new JobRunningEntity(quartzJob.getBean(), quartzJob.getMethod(), JSON.parseObject(quartzJob.getParams(), Map.class));
            // 执行任务
            jobRunningEntity.run();
            // 设置执行状态
            quartzJob.setJobStatus(1);
            quartzJob.setRunStatus(0);
            quartzLog.setExecStatus(1);
            log.debug("ActiveJob run success");
        }
        catch (Exception e) {
            log.error("ActiveJob run error, {}", e.getMessage());
            quartzJob.setJobStatus(0);
            quartzJob.setRunStatus(1);
            quartzLog.setExecStatus(0);
            // 获取异常信息
            String stackTrace = ThrowableUtil.getStackTrace(e);
            quartzLog.setErrorDesc(stackTrace);
            // ...
        }
        // 设置执行时间
        long endTime = System.currentTimeMillis();
        quartzLog.setExecTime(endTime - startTime);
        // 定时任务执行异常，修改执行状态，并暂停当前任务
        if (Objects.equals(0, quartzJob.getJobStatus()) && Objects.equals(1, quartzJob.getRunStatus())) {
            quartzJobService.updateJobStatus(quartzJob);
        }
        // 定时任务执行异常，告警通知
        if (Objects.equals(0, quartzJob.getJobStatus()) && Objects.equals(1, quartzJob.getRunStatus())) {
            if (Objects.equals(1, quartzJob.getIsAlarm()) && StringUtils.isNotBlank(quartzJob.getEmail())) {
                try {
                    this.alarmNotify(quartzLog);
                    quartzLog.setIsNotify(1);
                    log.debug("告警邮件发送成功");
                } catch (Exception e) {
                    log.error("告警邮件发送失败， {}", e.getMessage());
                    quartzLog.setIsNotify(0);
                }
            }
        }
        // 保存当前任务信息
        quartzJobService.updateById(quartzJob);
        // 保存任务日志
        quartzLog.setCreateTime(null);
        quartzLogService.save(quartzLog);
    }

    /**
     * 告警
     */
    public void alarmNotify (QuartzLog quartzLog) {
        EmailProcessService emailProcessService = SpringUtil.getBean(EmailProcessService.class);
        EmailSendDTO emailSendDTO = new EmailSendDTO();
        emailSendDTO.setReceiver(quartzLog.getEmail());
        emailSendDTO.setSubject("系统定时任务（" + quartzLog.getName() + "）告警邮件");
        emailSendDTO.setBody(buildEmailContent(quartzLog));
        emailSendDTO.setRelevance("quartz");
        EmailProcess emailProcess = emailProcessService.sendEmail(emailSendDTO);
        log.info("告警邮件发送返回结果，{}", JSON.toJSONString(emailProcess));
    }

    public String buildEmailContent(QuartzLog quartzLog) {
        StringBuffer sb = new StringBuffer();
        sb.append("<!DOCTYPE html> ");
        sb.append("<html> ");
        sb.append("<head> ");
        sb.append("    <title>系统定时任务（" + quartzLog.getName() + "）告警邮件</title> ");
        sb.append("    <link href=\"https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/5.2.3/css/bootstrap.css\" rel=\"stylesheet\"> ");
        sb.append("</head> ");
        sb.append("<body style=\"margin: 10px\"> ");
        sb.append("    <div class=\"alert alert-danger\" role=\"alert\" style=\"text-align: center;\"> ");
        sb.append("      系统定时任务（" + quartzLog.getName() + "）告警邮件 ");
        sb.append("    </div> ");
        sb.append("    <div class=\"card\"> ");
        sb.append("      <div class=\"card-header\"> ");
        sb.append("        系统定时任务基本信息 ");
        sb.append("      </div> ");
        sb.append("      <div class=\"card-body\"> ");
        sb.append("        <p class=\"card-text\">任务编号：" + quartzLog.getCode() + "</p> ");
        sb.append("        <p class=\"card-text\">任务名称：" + quartzLog.getName() + "</p> ");
        sb.append("        <p class=\"card-text\">定时任务类：" + quartzLog.getBean() + "</p>                         ");
        sb.append("        <p class=\"card-text\">参数(JSON)：" + quartzLog.getParams() + "</p> ");
        sb.append("        <p class=\"card-text\">cron表达式：" + quartzLog.getCronExpression() + "</p> ");
        sb.append("        <p class=\"card-text\">执行状态（1=执行成功，0= 执行异常）：" + quartzLog.getExecStatus() + "</p>     ");
        sb.append("        <p class=\"card-text\">负责人：" + quartzLog.getPrincipal() + "</p>     ");
        sb.append("        <p class=\"card-text\">邮箱：" + quartzLog.getEmail() + "</p>                                 ");
        sb.append("      </div> ");
        sb.append("    </div>     ");
        sb.append("    <div class=\"card\" style=\"margin-top: 10px\"> ");
        sb.append("      <div class=\"card-header\"> ");
        sb.append("        系统定时任务基本信息 ");
        sb.append("      </div>         ");
        sb.append("      <div class=\"card-body\"> ");
        sb.append("        <p class=\"card-text\">" + quartzLog.getErrorDesc().replaceAll("\n", "</br>") + "</p> ");
        sb.append("      </div> ");
        sb.append("    </div> ");
        sb.append("</div> ");
        sb.append("</body> ");
        sb.append("</html> ");
        return sb.toString();
    }

}
