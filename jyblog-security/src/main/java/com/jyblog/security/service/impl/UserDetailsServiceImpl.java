package com.jyblog.security.service.impl;

import com.jyblog.security.domain.SecurityUser;
import com.jyblog.system.permission.action.domain.PermissionAction;
import com.jyblog.system.permission.action.service.PermissionActionService;
import com.jyblog.system.user.domain.User;
import com.jyblog.system.user.service.UserService;
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
    private UserService userService;
    
    @Resource
    private PermissionActionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        List<PermissionAction> permissions = permissionService.getFromUser(user.getId());
        List<String> permissionCodes = permissions.stream().map(PermissionAction::getCode).collect(Collectors.toList());
        SecurityUser securityUser = new SecurityUser();
        securityUser.setCurrentUser(user);
        securityUser.setPermissions(permissionCodes);
        return securityUser;
    }
}
