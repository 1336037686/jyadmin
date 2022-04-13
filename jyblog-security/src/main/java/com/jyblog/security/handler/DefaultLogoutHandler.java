package com.jyblog.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-12 21:16 <br>
 * @description: DefaultLogoutHandler <br>
 */
@Slf4j
@Component("defaultLogoutHandler")
public class DefaultLogoutHandler implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("logout");
    }

}
