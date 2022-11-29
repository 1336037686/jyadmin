package com.jyadmin.system.job.manage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyadmin.system.job.manage.domain.QuartzJob;
import com.jyadmin.system.job.manage.service.QuartzJobService;
import com.jyadmin.system.job.manage.mapper.QuartzJobMapper;
import org.springframework.stereotype.Service;

/**
* @author 13360
* @description 针对表【sys_quartz_job(系统定时任务)】的数据库操作Service实现
* @createDate 2022-11-29 23:20:13
*/
@Service
public class QuartzJobServiceImpl extends ServiceImpl<QuartzJobMapper, QuartzJob>
    implements QuartzJobService{

}




