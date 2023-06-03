package com.jyadmin.security.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.GifCaptcha;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyadmin.config.properties.JyAuthProperties;
import com.jyadmin.config.properties.JyIdempotentProperties;
import com.jyadmin.consts.GlobalConstants;
import com.jyadmin.consts.ResultStatus;
import com.jyadmin.domain.UserCacheInfo;
import com.jyadmin.exception.ApiException;
import com.jyadmin.security.domain.PermissionAction;
import com.jyadmin.security.domain.SecurityUser;
import com.jyadmin.security.domain.User;
import com.jyadmin.security.mapper.AuthMapper;
import com.jyadmin.security.service.AuthService;
import com.jyadmin.security.service.CacheService;
import com.jyadmin.util.EncrypUtil;
import com.jyadmin.util.IpUtil;
import com.jyadmin.util.JWTUtil;
import com.jyadmin.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
* @author 13360
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2022-04-12 23:19:40
*/
@Service
public class AuthServiceImpl extends ServiceImpl<AuthMapper, User> implements AuthService {

    @Resource
    private AuthMapper authMapper;

    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private CacheService cacheService;

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private JyIdempotentProperties jyIdempotentProperties;
    @Resource
    private JyAuthProperties jyAuthProperties;

    @Override
    public Map<String, Object> login(HttpServletRequest request, String username, String password) {
        SecurityUser userDetails = (SecurityUser) userDetailsService.loadUserByUsername(username);
        // 解密密码
        String decryptPassword = EncrypUtil.decrypt(password);
        if (!passwordEncoder.matches(decryptPassword, userDetails.getPassword())) {
            throw new BadCredentialsException("密码不正确");
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 返回两个token
        String accessToken = JWTUtil.createAccessToken(userDetails.getUsername());
        String refreshToken = JWTUtil.createRefreshToken(userDetails.getUsername());

        // 保存用户信息到redis
        cacheService.save(buildUserCacheInfo(userDetails, request));

        // 返回token
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("accessToken", accessToken);
        tokenMap.put("refreshToken", refreshToken);

        return tokenMap;
    }

    private UserCacheInfo buildUserCacheInfo(SecurityUser userDetails, HttpServletRequest request) {
        String ip = IpUtil.getIp(request);
        UserCacheInfo userCacheInfo = new UserCacheInfo();
        User currentUser = userDetails.getCurrentUser();
        BeanUtil.copyProperties(currentUser, userCacheInfo);
        return userCacheInfo
                .setUsername(userDetails.getCurrentUser().getUsername())
                .setIpAddress(ip)
                .setIpArea(IpUtil.getAddressAndIsp(ip))
                .setBrowser(IpUtil.getBrowser(request))
                .setCreateTime(new Date())
                .setPermissions(userDetails.getPermissions());
    }

    @Override
    public String refreshToken(String refreshToken) {
        String username = JWTUtil.parseToken(refreshToken);
        String key = jyAuthProperties.getAuthUserPrefix() + ":" + username;
        // 判断当前refreshToken是否再缓存中存在，如果当前用户已经不存在了则代表已经退出
        boolean exists = redisUtil.exists(key);
        if (!exists) throw new ApiException(ResultStatus.REFRESH_TOKEN_ERROR);
        // 登陆续期
        boolean expire = redisUtil.expire(key, jyAuthProperties.getAuthUserExpiration(), TimeUnit.SECONDS);
        if (!expire) throw new ApiException(ResultStatus.REFRESH_TOKEN_ERROR);
        // 返回两个token
        return JWTUtil.createAccessToken(username);
    }

    @Override
    public User getByUserName(String userName) {
        return this.baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, userName));
    }

    @Override
    public List<String> getApiPermissionByUserId(String userId) {
        return authMapper.selectApiPermissionByUserId(userId);
    }

    @Override
    @Cacheable(value = "AuthService:getAllPermissions", key = "#root.methodName")
    public List<PermissionAction> getAllPermissions() {
        return authMapper.selectAllPermissions();
    }

    @Override
    public List<PermissionAction> getPermissionsByUserId(String userId) {
        return authMapper.selectPermissionsByUserId(userId);
    }

    @Override
    @Cacheable(value = "AuthService:getMenus", key = "#userId")
    public List<Map<String, Object>> getMenus(String userId) {
        List<Map<String, Object>> menus = this.authMapper.selectMenus(userId);
        List<Map<String, Object>> menuMaps = menus.stream().map(BeanUtil::beanToMap).collect(Collectors.toList());
        Map<String, Map<String, Object>> table = new HashMap<>();
        for (Map<String, Object> menu : menuMaps) table.put(menu.get("id").toString(), menu);
        for (Map<String, Object> menu : menuMaps) {
            if (menu.get("parentId") == null) continue;
            String parentId = menu.get("parentId").toString();
            Map<String, Object> parentMenu = table.get(parentId);
            if (parentMenu == null) continue;
            List<Map<String, Object>> children = (List<Map<String, Object>>) parentMenu.getOrDefault("children", new ArrayList<>());
            children.add(menu);
            parentMenu.put("children", children);
        }
        return menuMaps.stream().filter(x -> GlobalConstants.SYS_MENU_ROOT_PARENT_ID.equals(x.get("parentId"))).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "AuthService:getUserInfo", key = "#userId")
    public Map<String, Object> getUserInfo(String userId) {
        User user = getById(userId);
        List<Map<String, Object>> rolesMap = authMapper.selectRoles(userId);
        List<String> roles = rolesMap.stream()
                .filter(x -> Objects.nonNull(x.get("code")) && StringUtils.isNotBlank(x.get("code").toString()))
                .map(x -> x.get("code").toString())
                .collect(Collectors.toList());
        List<Map<String, Object>> menus = this.authMapper.selectMenus(userId);
        List<String> permissions = menus.stream()
                .filter(x -> Objects.nonNull(x.get("code")) && StringUtils.isNotBlank(x.get("code").toString()))
                .map(x -> x.get("code").toString())
                .collect(Collectors.toList());

        Map<String, Object> userInfo = BeanUtil.beanToMap(user);
        // 删除密码字段
        userInfo.remove("password");
        userInfo.put("roles", roles);
        userInfo.put("permissions", permissions);
        return userInfo;
    }

    @Override
    public void logout(String username) {
        cacheService.remove(username);
        SecurityContextHolder.clearContext();
    }

    @Override
    public String getIdempotentToken() {
        String token = StringUtils.join(DateUtil.format(LocalDateTime.now(), "yyyy-MM-dd-HH-mm-ss-SSS"), "-", new Snowflake().nextIdStr());
        String key = StringUtils.join(jyIdempotentProperties.getPrefix(), ":", token);
        redisUtil.setValue(key, jyIdempotentProperties.getDefaultValue(), jyIdempotentProperties.getDefaultPeriod(), TimeUnit.SECONDS);
        return token;
    }

    @Override
    public void getCaptcha(String uniqueId, HttpServletResponse response) {
        try {
            String key = StringUtils.join(jyAuthProperties.getVerificationCodePrefix() + ":" + uniqueId);
            GifCaptcha captcha = CaptchaUtil.createGifCaptcha(GlobalConstants.SYS_CAPTCHA_WIDTH, GlobalConstants.SYS_CAPTCHA_HEIGHT);
            captcha.setGenerator(new MathGenerator(GlobalConstants.SYS_CAPTCHA_MATH_GENERATOR_NUMBER_LENGTH)); // 自定义验证码内容为四则运算方式
            captcha.createCode(); // 重新生成code
            String code = captcha.getCode(); // 获取验证码
            redisUtil.setValue(key, code, jyAuthProperties.getVerificationCodeExpiration());
            captcha.write(response.getOutputStream());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(ResultStatus.CAPTCHA_FETCH_FAIL);
        }
    }


}




