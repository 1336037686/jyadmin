package com.jyadmin.aspect;

import cn.hutool.core.collection.CollectionUtil;
import com.jyadmin.annotation.Limit;
import com.jyadmin.config.properties.JyLimitProperties;
import com.jyadmin.consts.JyResultStatus;
import com.jyadmin.exception.JyBusinessException;
import com.jyadmin.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.ArrayUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 流量控制限制
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-07-07 00:21 <br>
 * @description: LimitAspect <br>
 */
@Slf4j
@Aspect
@Component
@ConditionalOnProperty(name = "jyadmin.limit.enabled", matchIfMissing = true)
public class LimitAspect {

    @Resource
    private RedisTemplate<Object,Object> redisTemplate;

    @Resource
    private JyLimitProperties limitProperties;

    @Pointcut("@annotation(com.jyadmin.annotation.Limit)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method signatureMethod = signature.getMethod();
        String fullMethodName = signatureMethod.getDeclaringClass().getName() + "." + signatureMethod.getName();
        Limit limit = signatureMethod.getAnnotation(Limit.class);


        String prefix = StringUtils.isNotBlank(limit.prefix()) ? limit.prefix() : limitProperties.getPrefix();
        int period = limit.period() > 0 ? limit.period() : limitProperties.getDefaultPeriod();
        int count = limit.count() > 0 ? limit.count() : limitProperties.getDefaultCount();
        String limitType = StringUtils.isNotBlank(limit.limitType()) ? limit.limitType() : limitProperties.getLimitType();
        String key = limit.key();
        if (StringUtils.isBlank(key)) {
            // 默认为方法全类名
            if(JyLimitProperties.LIMIT_TYPE_CUSTOMER.equals(limitType)) {
                key = fullMethodName;
            }
            else {
                key = IpUtil.getIp(request);
            }
        }
        // 构建缓存key
        List<Object> keys = Stream.of(StringUtils.join(prefix, ":", key, ":", request.getRequestURI().replace("/", "_"))).collect(Collectors.toList());
        // 构建lua限流脚本
        String luaScript = buildLimitLuaScript();
        RedisScript<Long> redisScript = new DefaultRedisScript<>(luaScript, Long.class);
        Long total = redisTemplate.execute(redisScript, keys, count, period);
        if (null != total && total.intValue() <= count) {
            log.info("第{}次访问key为 {}，方法为 [{}] 的接口, 请求路径为[{}]", total, keys, fullMethodName, request.getRequestURI());
            return joinPoint.proceed();
        } else {
            throw new JyBusinessException(JyResultStatus.LIMIT_EXCEEDED);
        }
    }

    /**
     * 限流脚本
     */
    private String buildLimitLuaScript() {
        return "local c" +
                "\nc = redis.call('get',KEYS[1])" +
                "\nif c and tonumber(c) > tonumber(ARGV[1]) then" +
                "\nreturn c;" +
                "\nend" +
                "\nc = redis.call('incr',KEYS[1])" +
                "\nif tonumber(c) == 1 then" +
                "\nredis.call('expire',KEYS[1],ARGV[2])" +
                "\nend" +
                "\nreturn c;";
    }

}
