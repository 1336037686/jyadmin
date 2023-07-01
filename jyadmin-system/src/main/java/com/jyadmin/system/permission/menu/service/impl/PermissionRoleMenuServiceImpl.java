package com.jyadmin.system.permission.menu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyadmin.system.permission.menu.domain.PermissionRoleMenu;
import com.jyadmin.system.permission.menu.mapper.PermissionRoleMenuMapper;
import com.jyadmin.system.permission.menu.service.PermissionRoleMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author 13360
* @description 针对表【tr_permission_role_menu(系统角色权限中间表)】的数据库操作Service实现
* @createDate 2022-04-13 23:23:16
*/
@Service
public class PermissionRoleMenuServiceImpl extends ServiceImpl<PermissionRoleMenuMapper, PermissionRoleMenu> implements PermissionRoleMenuService{

    @Resource
    private PermissionRoleMenuService permissionRoleMenuService;

    @Override
    public boolean saveFromRole(Long roleId, Set<Long> ids) {
        permissionRoleMenuService.remove(
                new LambdaQueryWrapper<PermissionRoleMenu>().eq(PermissionRoleMenu::getRoleId, roleId)
        );
        List<PermissionRoleMenu> permissionRoleMenus = ids.stream()
                .map(x -> new PermissionRoleMenu().setRoleId(roleId).setMenuId(x))
                .collect(Collectors.toList());
        permissionRoleMenuService.saveBatch(permissionRoleMenus);
        return true;
    }
}




