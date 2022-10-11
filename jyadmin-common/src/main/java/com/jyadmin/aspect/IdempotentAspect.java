package com.jyadmin.aspect;

import com.jyadmin.annotation.Idempotent;
import com.jyadmin.config.properties.JyIdempotentProperties;
import com.jyadmin.consts.ResultStatus;
import com.jyadmin.exception.ApiException;
import com.jyadmin.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 接口幂等实现切面
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-07-09 00:18 <br>
 * @description: IdempotentAspect <br>
 */
@Slf4j
@Aspect
@Component
@ConditionalOnProperty(name = "jyadmin.idempotent.enabled", matchIfMissing = false)
public class IdempotentAspect {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private JyIdempotentProperties idempotentProperties;

    @Pointcut("@annotation(com.jyadmin.annotation.Idempotent)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getParameter("idempotent-token");
        if (StringUtils.isBlank(token)) throw new ApiException(ResultStatus.REPEAT_OPERATION);

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method signatureMethod = signature.getMethod();
        String fullMethodName = signatureMethod.getDeclaringClass().getSimpleName() + "." + signatureMethod.getName();
        Idempotent idempotent = signatureMethod.getAnnotation(Idempotent.class);
        String keys = StringUtils.join(idempotentProperties.getPrefix(), ":", token);
        boolean exists = redisUtil.exists(keys);
        if (exists) {
            log.info("接口幂等：[{}] 请求Idempotent-Token为 [{}]，key为 [{}]，方法为 [{}] 的接口, 请求路径为[{}]", idempotent.name(), token, keys, fullMethodName, request.getRequestURI());
            redisUtil.delete(keys);
            return joinPoint.proceed();
        }
        else {
            throw new ApiException(ResultStatus.REPEAT_OPERATION);
        }
    }

}
