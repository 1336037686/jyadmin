package com.jyadmin.log.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyadmin.log.domain.Log;
import com.jyadmin.log.mapper.LogMapper;
import com.jyadmin.log.service.LogService;
import com.jyadmin.util.IpUtil;
import com.jyadmin.util.SecurityUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author 13360
* @description 针对表【sys_log(系统操作日志表)】的数据库操作Service实现
* @createDate 2022-04-30 01:10:58
*/
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

    @Override
    public void save(Object result, Integer execStatus, Integer execTime, ProceedingJoinPoint joinPoint, String errorMsg) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        com.jyadmin.log.annotation.Log logAnnotation = method.getAnnotation(com.jyadmin.log.annotation.Log.class);
        // 操作名称
        String title = logAnnotation.title();
        // 操作描述
        String desc = logAnnotation.desc();
        // ip地址
        String ipAddress = IpUtil.getIp(request);
        // ip地区
        String ipArea = IpUtil.getAddressAndIsp(ipAddress);
        // 浏览器
        String browser = IpUtil.getBrowser(request);
        // 设备
        String machine = IpUtil.getMachine(request);
        // 请求路径
        String requestPath = request.getRequestURI();
        // 请求类型
        String requestMethod = request.getMethod();
        // 请求参数
        List<Object> requestParam = Arrays.stream(joinPoint.getArgs())
                .filter(x -> !(x instanceof MultipartFile)) // 剔除文件类型
                .filter(x -> !(x instanceof HttpServletRequest)) // 剔除HttpServletRequest类型
                .filter(x -> !(x instanceof HttpServletResponse)) // 剔除HttpServletResponse类型
                .filter(x -> !(x instanceof InputStream)) // 剔除InputStream类型
                .filter(x -> !(x instanceof OutputStream)) // 剔除OutputStream类型
                .collect(Collectors.toList());
        // 请求时间
        LocalDateTime requestTime = LocalDateTime.now();
        // 请求类方法
        String methodName = joinPoint.getTarget().getClass().getName() + "." + method.getName();
        // 操作用户
        String username = "";
        try {
            username = SecurityUtil.getCurrentUsername();
        } catch (Exception ignored) {}

        // 返回结果
        String resultStr = Objects.isNull(result) ? "" : JSON.toJSONString(result);

        Log logObj = new Log()
                .setHandleName(title).setHandleDesc(desc)
                .setIpAddress(ipAddress).setIpArea(ipArea)
                .setBrowser(browser).setApplication(machine)
                .setRequestPath(requestPath).setRequestMethod(requestMethod)
                .setRequestParam(JSON.toJSONString(requestParam))
                .setRequestTime(requestTime).setMethod(methodName)
                .setExecuteStatus(execStatus).setExecuteTime((int) execTime)
                .setResponseData(resultStr)
                .setErrorDesc(errorMsg)
                .setUsername(username);
        this.save(logObj);
    }
}




