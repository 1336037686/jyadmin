package com.jyadmin.security.service.impl;

import com.jyadmin.security.domain.PermissionAction;
import com.jyadmin.security.domain.SecurityUser;
import com.jyadmin.security.domain.User;
import com.jyadmin.security.service.AuthService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authService.getByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        List<PermissionAction> permissions = authService.getPermissions(user.getId());
        List<String> permissionCodes = permissions.stream().map(PermissionAction::getCode).collect(Collectors.toList());
        SecurityUser securityUser = new SecurityUser();
        securityUser.setCurrentUser(user);
        securityUser.setPermissions(permissionCodes);
        return securityUser;
    }
}
