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
import java.util.*;
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

    @Override
    public List<Map<String, Object>> getTree() {
        List<Map<String, Object>> treeList = permissionActionMapper.selectTree();

        // 获取分组列表
        List<Map<String, Object>> groups = treeList.stream()
                .map(x -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("groupId", x.get("groupId"));
                    map.put("groupName", x.get("groupName"));
                    map.put("groupCode", x.get("groupCode"));
                    map.put("type", "group");
                    return map;
                })
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing((o) -> o.get("groupId").toString()))),
                        ArrayList::new));
        Map<String, Map<String, Object>> groupsMapping = new HashMap<>();
        for (Map<String, Object> group : groups) groupsMapping.put(group.get("groupId").toString(), group);

        // 归类下属接口
        treeList.stream().map(x -> {
            Map<String, Object> map = new HashMap<>();
            map.put("groupId", x.get("groupId"));
            map.put("actionId", x.get("actionId"));
            map.put("actionName", x.get("actionName"));
            map.put("actionCode", x.get("actionCode"));
            map.put("type", "action");
            return map;
        }).forEach(x -> {
            if (Objects.isNull(x.get("actionId"))) return;
            Map<String, Object> group = groupsMapping.get(x.get("groupId").toString());
            List<Map<String, Object>> children = (List<Map<String, Object>>) group.getOrDefault("children", new ArrayList<>());
            children.add(x);
            group.put("children", children);
        });
        return groups;
    }
}




