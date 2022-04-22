package com.jyblog.system.permission.menu.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyblog.system.permission.menu.domain.PermissionMenu;
import com.jyblog.system.permission.menu.domain.PermissionRoleMenu;
import com.jyblog.system.permission.menu.mapper.PermissionMenuMapper;
import com.jyblog.system.permission.menu.model.vo.PermissionMenuQueryVO;
import com.jyblog.system.permission.menu.service.PermissionMenuService;
import com.jyblog.system.permission.menu.service.PermissionRoleMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author 13360
* @description 针对表【sys_permission_menu(系统权限菜单表)】的数据库操作Service实现
* @createDate 2022-04-13 23:21:40
*/
@Service
public class PermissionMenuServiceImpl extends ServiceImpl<PermissionMenuMapper, PermissionMenu> implements PermissionMenuService{

    @Resource
    private PermissionRoleMenuService permissionRoleMenuService;

    @Resource
    private PermissionMenuMapper permissionMenuMapper;

    @Override
    public boolean saveFromRole(String roleId, Set<String> ids) {
        this.permissionRoleMenuService.remove(new LambdaQueryWrapper<PermissionRoleMenu>().eq(PermissionRoleMenu::getRoleId, roleId));
        List<PermissionRoleMenu> roleMenus = ids.stream().map(x -> new PermissionRoleMenu().setRoleId(roleId).setMenuId(x)).collect(Collectors.toList());
        permissionRoleMenuService.saveBatch(roleMenus);
        return true;
    }

    @Override
    public List<Map<String, Object>> getFromUser(String userId) {
        List<PermissionMenu> menus = this.permissionMenuMapper.selectFromUser(userId);
        List<Map<String, Object>> menuMaps = menus.stream().map(BeanUtil::beanToMap).collect(Collectors.toList());
        Map<String, Map<String, Object>> table = new HashMap<>();
        for (Map<String, Object> menu : menuMaps) table.put(menu.get("id").toString(), menu);
        for (Map<String, Object> menu : menuMaps) {
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
    public List<Map<String, Object>> getLayer(PermissionMenuQueryVO vo) {
        List<PermissionMenu> permissionMenus = this.getBaseMapper().selectList(
                new LambdaQueryWrapper<PermissionMenu>()
                        .like(StringUtils.isNotBlank(vo.getName()), PermissionMenu::getName, vo.getName())
                        .like(StringUtils.isNotBlank(vo.getCode()), PermissionMenu::getCode, vo.getCode())
                        .eq(Objects.nonNull(vo.getParentId()), PermissionMenu::getParentId, vo.getParentId())
                        .like(StringUtils.isNotBlank(vo.getUrl()), PermissionMenu::getUrl, vo.getUrl())
                        .like(StringUtils.isNotBlank(vo.getPath()), PermissionMenu::getPath, vo.getPath())
                        .eq(Objects.nonNull(vo.getCache()), PermissionMenu::getCache, vo.getCache())
                        .eq(Objects.nonNull(vo.getVisiable()), PermissionMenu::getVisiable, vo.getVisiable())
                        .eq(Objects.nonNull(vo.getLink()), PermissionMenu::getLink, vo.getLink())
                        .eq(Objects.nonNull(vo.getStatus()), PermissionMenu::getStatus, vo.getStatus())
        );

        List<Map<String, Object>> menuMaps = permissionMenus.stream().map(BeanUtil::beanToMap).collect(Collectors.toList());
        Set<String> childrenMenus = new HashSet<>();
        Map<String, Map<String, Object>> table = new HashMap<>();
        for (Map<String, Object> menu : menuMaps) table.put(menu.get("id").toString(), menu);
        for (Map<String, Object> menu : menuMaps) {
            Map<String, Object> parentMenu = table.get(menu.get("parentId").toString());
            if (Objects.isNull(parentMenu)) continue;
            List<Map<String, Object>> children = (List<Map<String, Object>>) parentMenu.getOrDefault("children", new ArrayList<>());
            children.add(menu);
            parentMenu.put("children", children);
            childrenMenus.add(menu.get("id").toString());
        }
        // 获取所有顶级节点
        return menuMaps.stream().filter(x -> !childrenMenus.contains(x.get("id").toString())).collect(Collectors.toList());
    }
}




