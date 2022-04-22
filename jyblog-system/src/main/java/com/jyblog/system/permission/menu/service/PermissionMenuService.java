package com.jyblog.system.permission.menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jyblog.system.permission.menu.domain.PermissionMenu;
import com.jyblog.system.permission.menu.model.vo.PermissionMenuQueryVO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
* @author 13360
* @description 针对表【sys_permission_menu(系统权限菜单表)】的数据库操作Service
* @createDate 2022-04-13 23:21:40
*/
public interface PermissionMenuService extends IService<PermissionMenu> {

    boolean saveFromRole(String roleId, Set<String> ids);

    List<Map<String, Object>> getFromUser(String userId);

    List<Map<String, Object>> getLayer(PermissionMenuQueryVO vo);
}
