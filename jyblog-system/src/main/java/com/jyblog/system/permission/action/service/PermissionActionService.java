package com.jyblog.system.permission.action.service;

import com.jyblog.system.permission.action.domain.PermissionAction;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
* @author 13360
* @description 针对表【sys_permission_action(系统权限动作表)】的数据库操作Service
* @createDate 2022-04-13 23:20:54
*/
public interface PermissionActionService extends IService<PermissionAction> {

    boolean saveFromMenu(String menuId, Set<String> ids);

    List<PermissionAction> getFromUser(String userId);

    List<Map<String, Object>> getTree();
}
