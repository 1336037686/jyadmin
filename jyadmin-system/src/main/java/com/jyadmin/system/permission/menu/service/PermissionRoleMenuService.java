package com.jyadmin.system.permission.menu.service;

import com.jyadmin.system.permission.menu.domain.PermissionRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
* @author 13360
* @description 针对表【tr_permission_role_menu(系统角色权限中间表)】的数据库操作Service
* @createDate 2022-04-13 23:23:16
*/
public interface PermissionRoleMenuService extends IService<PermissionRoleMenu> {

    boolean saveFromRole(Long roleId, Set<Long> ids);

}
