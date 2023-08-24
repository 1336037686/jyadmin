package com.jyadmin.security.domain;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import com.jyadmin.config.properties.JyAuthProperties;
import com.jyadmin.config.properties.JyJwtProperties;
import com.jyadmin.consts.GlobalConstants;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
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
@Accessors(chain = true)
public class SecurityUser implements UserDetails {

    // 当前登录用户
    private transient User currentUser;
    // 当前权限
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
        if (CollectionUtils.isEmpty(permissions)) return new ArrayList<>();
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
        JyAuthProperties authProperties = SpringUtil.getBean(JyAuthProperties.class);
        return authProperties.getAuthloginAttempts() >= currentUser.getLoginAttempts();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return GlobalConstants.SysStatus.ON.getValue().equals(currentUser.getStatus());
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
