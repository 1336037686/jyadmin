package com.jyblog.security.domain;

import com.alibaba.fastjson.JSON;
import com.jyblog.system.user.domain.User;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-14 00:30 <br>
 * @description: SecurityUser <br>
 */
@Data
public class SecurityUser implements UserDetails {

    //当前登录用户
    private transient User currentUser;
    //当前权限
    private List<String> permissions;

    public SecurityUser() {
    }
    public SecurityUser(User user) {
        if (user != null) {
            this.currentUser = user;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.permissions.stream()
                .filter(StringUtils::isNotBlank)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.currentUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.currentUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    // 重要，解决Util工具类无法获取SecurityUser 问题
    @Override
    public String toString() {
        return "{" +
                "\"currentUser\": " + JSON.toJSONString(currentUser) +
                ", \"permissions\": " + JSON.toJSONString(permissions) +
                "}";
    }
}
