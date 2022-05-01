package com.jyblog.security.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyblog.security.domain.PermissionAction;
import com.jyblog.security.domain.User;
import com.jyblog.security.mapper.AuthMapper;
import com.jyblog.security.service.AuthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
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

    @Override
    public User getByUserName(String userName) {
        return this.baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, userName));
    }

    @Override
    public List<PermissionAction> getPermissions(String userId) {
        return authMapper.selectPermissions(userId);
    }

    @Override
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
        return menuMaps.stream().filter(x -> "0".equals(x.get("parentId"))).collect(Collectors.toList());
    }

    @Override
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
        userInfo.put("roles", roles);
        userInfo.put("permissions", permissions);
        return userInfo;
    }
}




