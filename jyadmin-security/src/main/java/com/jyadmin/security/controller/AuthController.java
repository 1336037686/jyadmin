package com.jyadmin.security.controller;

import com.jyadmin.annotation.Limit;
import com.jyadmin.domain.Result;
import com.jyadmin.log.annotation.Log;
import com.jyadmin.security.domain.UserLoginVO;
import com.jyadmin.security.service.AuthService;
import com.jyadmin.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    /**
     * 登录
     */
    @Limit
    @Log(title = "系统认证：用户登录", desc = "")
    @ApiOperation(value = "用户登录", notes = "")
    @PostMapping(value = "/login")
    public Result<Map<String, Object>> doLogin(@RequestBody UserLoginVO vo, HttpServletRequest request) {
        Map<String, Object> token = authService.login(request, vo.getUsername(), vo.getPassword());
        return Result.ok(token);
    }

    /**
     * 退出登录
     */
    @ApiOperation(value = "退出登录", notes = "")
    @PostMapping(value = "/logout")
    public Result<Object> doLogout() {
        String username = SecurityUtil.getCurrentUsername();
        authService.logout(username);
        return Result.ok();
    }

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

    // TODO: 获取登录用户列表

    // TODO: 强制下线



}
