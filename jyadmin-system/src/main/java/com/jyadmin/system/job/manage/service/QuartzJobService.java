package com.jyadmin.system.job.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jyadmin.system.job.manage.domain.QuartzJob;

import java.util.Collection;

/**
* @author 13360
* @description 针对表【sys_quartz_job(系统定时任务)】的数据库操作Service
* @createDate 2022-11-29 23:20:14
*/
public interface QuartzJobService extends IService<QuartzJob> {

    boolean saveJob(QuartzJob entity);

    boolean removeJobs(Collection<?> list);

    boolean updateJob(QuartzJob entity);

    boolean updateJobStatus(QuartzJob quartzJob);

}
