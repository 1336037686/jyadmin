package com.jyadmin.log.aspect;

import com.jyadmin.util.ThrowableUtil;
import com.jyadmin.log.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 操作日志切面
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-29 23:53 <br>
 * @description: LogAspect <br>
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    @Resource
    private LogService logService;

    @Pointcut("@annotation(com.jyadmin.log.annotation.Log)")
    public void logPointcut() {
    }

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param joinPoint join point for advice
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 开始执行时间
        long startTime = System.currentTimeMillis();
        try {
            // 执行方法， 返回数据
            Object result = joinPoint.proceed();
            // 执行结束时间
            long endTime = System.currentTimeMillis();
            // 执行时间
            Integer execTime = Math.toIntExact((endTime - startTime));
            logService.save(result, 1, execTime, joinPoint, null);
            return result;
        } catch (Throwable e) {
            // 执行结束时间
            long endTime = System.currentTimeMillis();
            // 执行时间 ms
            Integer execTime = Math.toIntExact((endTime - startTime));
            logService.save(null, 0, execTime, joinPoint, ThrowableUtil.getStackTrace(e));
            throw e;
        }
    }
}
