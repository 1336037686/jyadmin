package com.jyadmin.security.handler;

import com.jyadmin.consts.ResultStatus;
import com.jyadmin.domain.Result;
import com.jyadmin.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理器当认证成功的用户访问受保护的资源，但是权限不够
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-12 23:52 <br>
 * @description: DefaultAccessDeniedHandler <br>
 */
@Slf4j
@Component("defaultAccessDeniedHandler")
public class DefaultAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("DefaultAccessDeniedHandler 认证通过，但是没权限处理, {}", accessDeniedException.getMessage());
        // 权限不足
        ResponseUtil.out(response, Result.fail(ResultStatus.INSUFFICIENT_PERMISSIONS));
    }
}
