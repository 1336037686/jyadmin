package com.jyadmin.log.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jyadmin.log.domain.Log;
import org.aspectj.lang.ProceedingJoinPoint;

/**
* @author 13360
* @description 针对表【sys_log(系统操作日志表)】的数据库操作Service
* @createDate 2022-04-30 01:10:58
*/
public interface LogService extends IService<Log> {

    /**
     *
     * @param result 执行结果
     * @param execStatus 执行状态 0 失败 | 1 成功
     * @param execTime 执行时间（s）
     * @param joinPoint 切点
     * @param errorMsg 错误信息
     */
    void save(Object result, Integer execStatus, Integer execTime, ProceedingJoinPoint joinPoint, String errorMsg);
}
