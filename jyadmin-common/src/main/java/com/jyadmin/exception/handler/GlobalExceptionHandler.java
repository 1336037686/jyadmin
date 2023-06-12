package com.jyadmin.exception.handler;

import com.jyadmin.consts.ResultStatus;
import com.jyadmin.domain.Result;
import com.jyadmin.exception.ApiException;
import com.jyadmin.util.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 *
 * 主要异常如下：
 * 1、请求参数校验异常处理
 * 2、权限异常处理
 * 3、自定义异常处理
 * 4、其它系统异常处理
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-05 02:15 <br>
 * @description: GlobalExceptionHandler <br>
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理所有不可知的异常
     */
    @ExceptionHandler(Throwable.class)
    public Result<Object> handleException(Throwable e){
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        return Result.fail(ResultStatus.FAIL);
    }

    /**
     * 处理所有登录失败异常
     */
    @ExceptionHandler(AuthenticationException.class)
    public Result<Object> handleAuthenticationException(AuthenticationException e) {
        // 账号被锁定
        if (e instanceof LockedException) return Result.fail(ResultStatus.ACCOUNT_LOCKOUT);
        // 密码过期
        if (e instanceof CredentialsExpiredException) return Result.fail(ResultStatus.PASSWORD_EXPIRATION);
        // 账户过期
        if (e instanceof AccountExpiredException) return Result.fail(ResultStatus.ACCOUNT_EXPIRATION);
        // 账户被禁用
        if (e instanceof DisabledException) return Result.fail(ResultStatus.ACCOUNT_DISABLED);
        // 用户名或者密码输入错误
        if (e instanceof BadCredentialsException) return Result.fail(ResultStatus.USERNAME_PASSWORD_ERROR);
        // 用户不存在
        if (e instanceof UsernameNotFoundException) return Result.fail(ResultStatus.USERNAME_PASSWORD_ERROR);
        // 其他
        return Result.fail(ResultStatus.FAIL);
    }

    /**
     * AccessDeniedException 权限不足异常处理
     * 认证成功的用户访问受保护的资源，但是权限不够
     * 用以解决动态菜单权限配置 DefaultAccessDeniedHandler 不生效，而会被全局异常捕获的问题
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Result<Object> handleAccessDeniedException(AccessDeniedException e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        return Result.fail(ResultStatus.INSUFFICIENT_PERMISSIONS);
    }

    /**
     * 处理所有接口数据验证异常
     * 需要验证的参数上加上了@Valid注解，如果验证失败，它将抛出MethodArgumentNotValidException
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        List<FieldError> fieldErrors = ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors();
        List<String> msgList = fieldErrors.stream().map(FieldError :: getDefaultMessage).collect(Collectors.toList());
        return Result.fail(ResultStatus.PARAM_ERROR, String.join(ResultStatus.PARAM_ERROR.getReasonPhrase(), String.join(",", msgList)));
    }

    /**
     * 处理所有接口业务异常
     */
    @ExceptionHandler(ApiException.class)
    public Result<Object> handleApiException(ApiException e){
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        return Result.fail(e.getCode(), e.getMsg());
    }

}
