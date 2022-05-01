package com.jyblog.security.controller;

import com.jyblog.domain.Result;
import com.jyblog.security.service.AuthService;
import com.jyblog.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-30 18:29 <br>
 * @description: AuthController <br>
 */
@Slf4j
@Api(value = "系统认证", tags = "系统：系统认证接口")
@RequestMapping("/api/auth")
@RestController
public class AuthController {

    @Resource
    private AuthService authService;

    // 用户注册

    // 获取用户信息
    @ApiOperation(value = "获取用户信息", notes = "")
    @GetMapping("/info")
    public Result<Map<String, Object>> doQueryUserInfo() {
        String userId = SecurityUtil.getCurrentUserId();
        Map<String, Object> userInfo = this.authService.getUserInfo(userId);
        return Result.ok(userInfo);
    }

    @ApiOperation(value = "获取用户菜单", notes = "")
    @GetMapping("/menus")
    public Result<List<Map<String, Object>>> doQueryMenus() {
        String userId = SecurityUtil.getCurrentUserId();
        List<Map<String, Object>> menus = this.authService.getMenus(userId);
        return Result.ok(menus);
    }

    @ApiOperation(value = "退出登录", notes = "")
    @PostMapping("/logout")
    public Result<Object> doLogout() {
        return Result.ok();
    }
}
