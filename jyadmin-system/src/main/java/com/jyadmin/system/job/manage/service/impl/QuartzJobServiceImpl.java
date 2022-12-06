package com.jyadmin.system.job.manage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyadmin.system.job.common.QuartzManage;
import com.jyadmin.system.job.manage.domain.QuartzJob;
import com.jyadmin.system.job.manage.mapper.QuartzJobMapper;
import com.jyadmin.system.job.manage.service.QuartzJobService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Objects;

/**
* @author 13360
* @description 针对表【sys_quartz_job(系统定时任务)】的数据库操作Service实现
* @createDate 2022-11-29 23:20:13
*/
@Service
public class QuartzJobServiceImpl extends ServiceImpl<QuartzJobMapper, QuartzJob> implements QuartzJobService{

    @Resource
    private QuartzManage quartzManage;

    @Transactional(rollbackFor = Exception.class)
    public boolean saveJob(QuartzJob entity) {
        boolean save = super.save(entity);
        quartzManage.addJob(entity);
        return save;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateJob(QuartzJob entity) {
        boolean update = super.updateById(entity);
        quartzManage.updateJobCron(entity);
        return update;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean removeJobs(Collection<?> list) {
        for (Object id : list) {
            QuartzJob quartzJob = getById((String) id);
            quartzManage.deleteJob(quartzJob);
        }
        boolean remove = super.removeByIds(list);
        return remove;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateJobStatus(QuartzJob entity) {
        if (Objects.equals(1, entity.getJobStatus())) {
            // resumeJob恢复job
            quartzManage.resumeJob(entity);
        }
        if (Objects.equals(0, entity.getJobStatus())) {
            // pauseJob暂停job
            quartzManage.pauseJob(entity);
        }
        return super.updateById(entity);
    }
}




