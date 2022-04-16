package com.jyblog.system.permission.action.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyblog.system.permission.action.domain.PermissionAction;
import com.jyblog.system.permission.action.domain.PermissionMenuAction;
import com.jyblog.system.permission.action.mapper.PermissionActionMapper;
import com.jyblog.system.permission.action.service.PermissionActionService;
import com.jyblog.system.permission.action.service.PermissionMenuActionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author 13360
* @description 针对表【sys_permission_action(系统权限动作表)】的数据库操作Service实现
* @createDate 2022-04-13 23:20:54
*/
@Service
public class PermissionActionServiceImpl extends ServiceImpl<PermissionActionMapper, PermissionAction> implements PermissionActionService{

    @Resource
    private PermissionMenuActionService permissionMenuActionService;

    @Resource
    private PermissionActionMapper permissionActionMapper;

    @Transactional
    @Override
    public boolean saveFromMenu(String menuId, Set<String> ids) {
        permissionMenuActionService.remove(
                new LambdaQueryWrapper<PermissionMenuAction>().eq(PermissionMenuAction::getMenuId, menuId)
        );
        List<PermissionMenuAction> permissionMenuActions = ids.stream()
                .map(x -> new PermissionMenuAction().setMenuId(menuId).setActionId(x))
                .collect(Collectors.toList());
        permissionMenuActionService.saveBatch(permissionMenuActions);
        return true;
    }

    @Override
    public List<PermissionAction> getFromUser(String userId) {
        return permissionActionMapper.selectFromUser(userId);
    }
}




