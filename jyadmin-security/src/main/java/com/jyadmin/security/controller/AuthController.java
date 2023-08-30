package com.jyadmin.security.controller;

import cn.hutool.captcha.generator.MathGenerator;
import com.jyadmin.annotation.RateLimit;
import com.jyadmin.config.properties.JyAuthProperties;
import com.jyadmin.config.properties.JyRsaProperties;
import com.jyadmin.consts.GlobalConstants;
import com.jyadmin.consts.ResultStatus;
import com.jyadmin.domain.Result;
import com.jyadmin.exception.ApiException;
import com.jyadmin.log.annotation.Log;
import com.jyadmin.security.domain.UserInfo;
import com.jyadmin.security.domain.UserLoginVO;
import com.jyadmin.security.domain.UserRegisterVO;
import com.jyadmin.security.service.AuthService;
import com.jyadmin.util.JWTUtil;
import com.jyadmin.util.RedisUtil;
import com.jyadmin.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    private RedisUtil redisUtil;
    @Resource
    private AuthService authService;
    @Resource
    private JyAuthProperties jyAuthProperties;
    @Resource
    private JyRsaProperties jyRsaProperties;

    /**
     * 登录
     * @return /
     */
    @RateLimit
    @Log(title = "系统认证：用户登录", desc = "")
    @ApiOperation(value = "用户登录", notes = "")
    @PostMapping(value = "/login")
    public Result<Map<String, Object>> doLogin(@RequestBody @Valid UserLoginVO vo, HttpServletRequest request) {
        // 验证验证码是否正确
        Object value = redisUtil.getValue(jyAuthProperties.getVerificationCodePrefix() + ":" + vo.getUniqueId());
        if (Objects.isNull(value)) throw new ApiException(ResultStatus.CAPTCHA_EXPIRED);
        if (!new MathGenerator().verify(value.toString(), vo.getCaptcha())) throw new ApiException(ResultStatus.CAPTCHA_INPUT_ERROR);
        Map<String, Object> token = authService.login(request, vo.getUsername(), vo.getPassword());
        return Result.ok(token);
    }

    /**
     * 注册
     */
    @RateLimit
    @Log(title = "系统认证：用户注册", desc = "")
    @ApiOperation(value = "用户注册", notes = "")
    @PostMapping(value = "/register")
    public Result<Map<String, Object>> doRegister(@RequestBody @Valid UserRegisterVO vo, HttpServletRequest request) {
        // 验证验证码是否正确
        Object value = redisUtil.getValue(GlobalConstants.SYS_SMS_VERIFICATION_CODE_PREFIX + ":" + vo.getUniqueId());
        if (Objects.isNull(value)) throw new ApiException(ResultStatus.CAPTCHA_EXPIRED);
        if (!Objects.equals(value.toString(), vo.getCaptcha())) throw new ApiException(ResultStatus.CAPTCHA_INPUT_ERROR);
        Map<String, Object> token = authService.register(request, vo);
        return Result.ok(token);
    }

    /**
     * 退出登录
     * @return /
     */
    @ApiOperation(value = "退出登录", notes = "")
    @PostMapping(value = "/logout")
    public Result<Object> doLogout() {
        String username = SecurityUtil.getCurrentUsername();
        authService.logout(username);
        return Result.ok();
    }

    /**
     * 登陆续期
     * @return /
     */
    @ApiOperation(value = "登陆续期", notes = "")
    @PostMapping(value = "/refreshToken")
    public Result<Object> doRefreshToken(@RequestHeader(GlobalConstants.SYS_LOGIN_REFRESH_TOKEN_PARAM_NAME) String refreshToken) {
        if (StringUtils.isBlank(refreshToken) || !JWTUtil.verify(refreshToken)) throw new ApiException(ResultStatus.REFRESH_TOKEN_NOT_EXIST);
        String accessToken = authService.refreshToken(refreshToken);
        return Result.ok(accessToken);
    }

    /**
     * 获取用户信息
     * @return /
     */
    @ApiOperation(value = "获取用户信息", notes = "")
    @GetMapping("/info")
    public Result<UserInfo> doQueryUserInfo() {
        Long userId = SecurityUtil.getCurrentUserId();
        UserInfo userInfo = this.authService.getUserInfo(userId);
        return Result.ok(userInfo);
    }

    /**
     * 获取用户菜单
     * @return /
     */
    @ApiOperation(value = "获取用户菜单", notes = "")
    @GetMapping("/menus")
    public Result<List<Map<String, Object>>> doQueryMenus() {
        Long userId = SecurityUtil.getCurrentUserId();
        List<Map<String, Object>> menus = this.authService.getMenus(userId);
        return Result.ok(menus);
    }

    /**
     * 获取幂等Token
     * @return /
     */
    @ApiOperation(value = "获取幂等Token", notes = "")
    @GetMapping("/idempotent-token")
    public Result<String> doQueryIdempotentToken() {
        String idempotentToken = this.authService.getIdempotentToken();
        return Result.ok(idempotentToken);
    }

    /**
     * 获取RSA加密公钥
     * @return /
     */
    @ApiOperation(value = "获取RSA加密公钥", notes = "")
    @GetMapping("/rsa-public-key")
    public Result<String> doQueryRsaPublicKey() {
        return Result.ok(jyRsaProperties.getPublicKey());
    }

    /**
     * 获取登录验证码
     * 验证码key GlobalConstant Code + ip
     * @param uniqueId 唯一标识
     */
    @ApiOperation(value = "获取登录验证码", notes = "")
    @GetMapping("/captcha/{uniqueId}")
    public void doQueryCaptcha(@PathVariable("uniqueId") String uniqueId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.authService.getCaptcha(uniqueId, response);
    }

}
