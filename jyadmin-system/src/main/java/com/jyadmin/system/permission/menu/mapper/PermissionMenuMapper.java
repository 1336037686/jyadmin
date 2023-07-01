package com.jyadmin.system.permission.menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jyadmin.system.permission.menu.domain.PermissionMenu;

import java.util.List;

/**
* @author 13360
* @description 针对表【sys_permission_menu(系统权限菜单表)】的数据库操作Mapper
* @createDate 2022-04-13 23:21:40
* @Entity com.jyblog.system.permission.menu.domain.PermissionMenu
*/
public interface PermissionMenuMapper extends BaseMapper<PermissionMenu> {

    List<PermissionMenu> selectFromUser(Long userId);
}




