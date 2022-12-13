package com.jyadmin.system.job.config;

import com.jyadmin.system.job.common.QuartzManage;
import com.jyadmin.system.job.manage.domain.QuartzJob;
import com.jyadmin.system.job.manage.service.QuartzJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 项目启动时重启定时任务
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-30 20:27 <br>
 * @description: JobApplicationRunner <br>
 */
@Slf4j
@Component
public class JobApplicationRunner implements ApplicationRunner {

    @Resource
    private QuartzJobService quartzJobService;

    @Resource
    private QuartzManage quartzManage;

    /**
     * 项目启动时重启定时任务
     */
    @Override
    public void run(ApplicationArguments applicationArguments) {
        log.info("========================== JobApplicationRunner 定时任务重启 START ===========================");
        List<QuartzJob> jobs = quartzJobService.list();
        jobs.forEach(quartzManage::addJob);
        log.info("========================== JobApplicationRunner 定时任务重启 END   ===========================");
    }



}
