package com.jyadmin.security.service.impl;

import com.google.common.collect.Lists;
import com.jyadmin.consts.GlobalConstants;
import com.jyadmin.security.domain.PermissionAction;
import com.jyadmin.security.domain.SecurityUser;
import com.jyadmin.security.domain.User;
import com.jyadmin.security.service.AuthService;
import com.jyadmin.security.service.CacheService;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-11 22:01 <br>
 * @description: UserDetailServiceImpl <br>
 */
@Service("UserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private AuthService authService;
    @Resource
    private CacheService cacheService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityUser securityUser = new SecurityUser();
        User user = authService.getByUserName(username);
        // 基础校验
        securityUser.setCurrentUser(user);
        if (Objects.isNull(securityUser.getCurrentUser())) throw new UsernameNotFoundException("用户不存在");
        if (Boolean.FALSE.equals(securityUser.isEnabled())) throw new DisabledException("账号已禁用");
        if (Boolean.FALSE.equals(securityUser.isAccountNonLocked())) throw new LockedException("账号已锁定");

        // 获取当前用户的接口权限，角色接口权限（api_permission_portion=根据角色菜单限制接口权限， api_permission_all=拥有全部接口权限）
        List<String> apiPermissions = authService.getApiPermissionByUserId(user.getId());
        // 是否拥有全部接口权限
        boolean hasAllApiPermission = apiPermissions.stream().anyMatch(x -> x.equals(GlobalConstants.SysApiPermission.API_PERMISSION_ALL.getCode()));
        List<PermissionAction> permissions = Lists.newArrayList();
        // 如果拥有全部接口权限则查询全部
        if (hasAllApiPermission) permissions = authService.getAllPermissions();
        // 否则根据用户ID查询用户所拥有的角色的可访问接口配置列表
        else permissions = authService.getPermissionsByUserId(user.getId());
        // 获取所有接口编码
        List<String> permissionCodes = permissions.stream().map(PermissionAction::getCode).collect(Collectors.toList());
        securityUser.setPermissions(permissionCodes);
        return securityUser;
    }
}
