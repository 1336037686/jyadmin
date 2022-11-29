package com.jyadmin.system.job.log.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyadmin.system.job.log.domain.QuartzLog;
import com.jyadmin.system.job.log.service.QuartzLogService;
import com.jyadmin.system.job.log.mapper.QuartzLogMapper;
import org.springframework.stereotype.Service;

/**
* @author 13360
* @description 针对表【sys_quartz_log(系统定时任务日志)】的数据库操作Service实现
* @createDate 2022-11-29 23:20:27
*/
@Service
public class QuartzLogServiceImpl extends ServiceImpl<QuartzLogMapper, QuartzLog>
    implements QuartzLogService{

}




