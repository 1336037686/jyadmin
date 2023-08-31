package com.jyadmin.security.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.GifCaptcha;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyadmin.config.properties.JyAuthProperties;
import com.jyadmin.config.properties.JyBaseProperties;
import com.jyadmin.config.properties.JyIdempotentProperties;
import com.jyadmin.consts.GlobalConstants;
import com.jyadmin.consts.ResultStatus;
import com.jyadmin.domain.UserCacheInfo;
import com.jyadmin.exception.ApiException;
import com.jyadmin.security.domain.*;
import com.jyadmin.security.mapper.AuthMapper;
import com.jyadmin.security.mapper.AuthUserRoleMapper;
import com.jyadmin.security.service.AuthService;
import com.jyadmin.security.service.CacheService;
import com.jyadmin.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Slf4j
@Service
public class AuthServiceImpl extends ServiceImpl<AuthMapper, User> implements AuthService {

    @Resource
    private AuthMapper authMapper;
    @Resource
    private AuthUserRoleMapper authUserRoleMapper;
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
    @Resource
    private JyBaseProperties jyBaseProperties;

    /**
     * 用户登录
     * @param request /
     * @param username 用户名
     * @param password 密码
     * @return /
     */
    @Override
    public Map<String, Object> login(HttpServletRequest request, String username, String password) {
        // 基础校验
        checkAccountLegal(username, password, request);

        // 登录信息构建
        SecurityUser userDetails = (SecurityUser) userDetailsService.loadUserByUsername(username);
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

    /**
     * 用户注册
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> register(HttpServletRequest request, UserRegisterVO vo) {
        log.info("用户注册，注册参数={}", vo);
        // 校验当前用户信息是否已经注册
        boolean exists = authMapper.exists(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, vo.getPhone())
                .or().eq(User::getPhone, vo.getPhone()));
        if (exists) throw new ApiException(ResultStatus.ACCOUNT_ALREADY_EXIST);

        String password = new BCryptPasswordEncoder().encode(GlobalConstants.DEFAULT_USER_PASSWORD);

        // 构建用户实体
        User user = new User();
        user.setUsername(vo.getPhone());
        user.setPassword(password);
        user.setNickname(RandomUtil.randomString(GlobalConstants.DEFAULT_USER_NICKNAME_GENERATE_LENGTH));
        user.setAvatar(GlobalConstants.DEFAULT_USER_AVATAR);
        user.setPhone(vo.getPhone());
        user.setType(GlobalConstants.SysUserType.MEMBER.getValue());
        user.setLoginAttempts(0);
        user.setLastLoginTime(LocalDateTime.now());
        user.setLastLoginIp(IpUtil.getIp(request));
        user.setStatus(GlobalConstants.SysStatus.ON.getValue());
        authMapper.insert(user);

        // 对用户赋予默认【会员】角色
        Long defaultRoleId = authMapper.selectRoleByCode(GlobalConstants.DEFAULT_USER_ROLE);
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(defaultRoleId);
        authUserRoleMapper.insert(userRole);

        // 创建成功后自动登录
        Map<String, Object> login = this.login(request, user.getUsername(), RsaUtil.encrypt(GlobalConstants.DEFAULT_USER_PASSWORD));
        log.info("用户注册：{}注册成功", user.getUsername());
        return login;
    }

    /**
     * 校验账户合法性
     * @param username /
     * @param password /
     */
    public void checkAccountLegal(String username, String password, HttpServletRequest request) {
        User user = getByUserName(username);
        // 用户名校验
        if (Objects.isNull(user)) throw new UsernameNotFoundException("用户不存在");
        // 禁用校验
        if (GlobalConstants.SysStatus.OFF.getValue().equals(user.getStatus())) throw new DisabledException("账号已禁用");
        // 锁定校验
        if (cacheService.isLocked(username)) throw new LockedException("账号已锁定");
        // 密码校验
        if (!passwordEncoder.matches(RsaUtil.decrypt(password), user.getPassword())) {
            // 增加尝试登录次数
            authMapper.updateById(buildUserLoginFailInfo(user));
            // 如果登录次数大于限制次数，则锁定账户
            if (jyAuthProperties.getAuthloginAttempts() <= user.getLoginAttempts()) cacheService.lockUser(username);
            throw new BadCredentialsException("密码不正确");
        }
        // 校验通过，更新用户登录信息
        authMapper.updateById(buildUserLoginInfo(user, request));
    }

    /**
     * 构建用户登录成功信息
     * @param user 用户
     * @param request /
     * @return User
     */
    private User buildUserLoginInfo(User user, HttpServletRequest request) {
        String ip = IpUtil.getIp(request);
        user.setLoginAttempts(0);
        user.setLastLoginTime(LocalDateTime.now());
        user.setLastLoginIp(ip);
        return user;
    }

    /**
     * 构建用户登录失败信息
     * @param user 用户
     * @return User
     */
    private User buildUserLoginFailInfo(User user) {
        user.setLoginAttempts(user.getLoginAttempts() + 1);
        return user;
    }

    /**
     * 构建用户缓存信息
     * @param userDetails 用户信息
     * @param request /
     * @return UserCacheInfo
     */
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

    /**
     * 刷新Access Token
     * @param refreshToken 刷新Token
     * @return /
     */
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

    /**
     * 根据用户名获取用户信息
     * @param userName /
     * @return /
     */
    @Override
    public User getByUserName(String userName) {
        return this.baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, userName));
    }

    /**
     * 根据用户名ID获取用户权限信息
     * @param userId /
     * @return /
     */
    @Override
    public List<String> getApiPermissionByUserId(Long userId) {
        return authMapper.selectApiPermissionByUserId(userId);
    }

    /**
     * 获取所有接口权限
     * @return /
     */
    @Override
    @Cacheable(value = "AuthService:getAllPermissions", cacheManager = "commonCacheManager", key = "#root.methodName")
    public List<PermissionAction> getAllPermissions() {
        return authMapper.selectAllPermissions();
    }

    /**
     * 根据用户ID获取用户接口权限
     * @return /
     */
    @Override
    public List<PermissionAction> getPermissionsByUserId(Long userId) {
        return authMapper.selectPermissionsByUserId(userId);
    }

    /**
     * 根据用户ID获取菜单
     * @param userId /
     * @return /
     */
    @Override
    @Cacheable(value = "AuthService:getMenus", cacheManager = "commonCacheManager", key = "#userId")
    public List<Map<String, Object>> getMenus(Long userId) {
        List<Map<String, Object>> menus = this.authMapper.selectMenus(userId);

        List<Map<String, Object>> menuMaps = menus.stream().map(BeanUtil::beanToMap).peek(x -> {
            x.put("id", DataUtil.getValueForMap(x, "id"));
            x.put("parentId", DataUtil.getValueForMap(x, "parentId"));
        }).collect(Collectors.toList());

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
        return menuMaps.stream().filter(x -> Objects.equals(GlobalConstants.SYS_MENU_ROOT_PARENT_ID.toString(), x.get("parentId"))).collect(Collectors.toList());
    }

    /**
     * 根据用户ID获取用户信息
     * @param userId /
     * @return /
     */
    @Override
    @Cacheable(value = "AuthService:getUserInfo", cacheManager = "commonCacheManager", key = "#userId")
    public UserInfo getUserInfo(Long userId) {
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

        UserInfo userInfo = new UserInfo();
        BeanUtil.copyProperties(user, userInfo);
        userInfo.setRoles(roles);
        userInfo.setPermissions(permissions);
        return userInfo;
    }

    /**
     * 退出登录
     * @param username 用户名
     */
    @Override
    public void logout(String username) {
        cacheService.remove(username);
        SecurityContextHolder.clearContext();
    }

    /**
     * 生成幂等Token
     * @return /
     */
    @Override
    public String getIdempotentToken() {
        String token = StringUtils.join(DateUtil.format(LocalDateTime.now(), "yyyy-MM-dd-HH-mm-ss-SSS"), "-", new Snowflake().nextIdStr());
        String key = StringUtils.join(jyIdempotentProperties.getPrefix(), ":", token);
        redisUtil.setValue(key, jyIdempotentProperties.getDefaultValue(), jyIdempotentProperties.getDefaultPeriod(), TimeUnit.SECONDS);
        return token;
    }

    /**
     * 生成验证码
     * @param uniqueId 唯一ID
     * @param response /
     */
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




