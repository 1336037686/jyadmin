package com.jyadmin.config;

import com.jyadmin.config.properties.JyBaseProperties;
import com.jyadmin.util.SecurityUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 校验接口权限
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-16 17:23 <br>
 * @description: JySecurityPermissionHandler <br>
 */
@Service("jy")
public class SecurityPermissionHandler {

    @Resource
    private JyBaseProperties baseProperties;

    /**
     * 校验当前用户是否拥有权限
     * @param permissions
     * @return
     */
    public Boolean check(String ...permissions){
        // 获取当前用户的所有权限
        UserDetails currentUser = SecurityUtil.getCurrentUser();
        List<String> jyPermissions = currentUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        // 判断当前用户的所有权限是否包含接口上定义的权限, 如果账号是管理员账号则全部放行
        return baseProperties.getSuperAdmins().contains(currentUser.getUsername().trim()) ||
                Arrays.stream(permissions).anyMatch(jyPermissions::contains);
    }

}
