package com.jyadmin.system.permission.action.mapper;

import com.jyadmin.system.permission.action.domain.PermissionAction;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
* @author 13360
* @description 针对表【sys_permission_action(系统权限动作表)】的数据库操作Mapper
* @createDate 2022-04-13 23:20:54
* @Entity com.jyblog.system.permission.action.domain.PermissionAction
*/
public interface PermissionActionMapper extends BaseMapper<PermissionAction> {


    List<PermissionAction> selectFromUser(Long userId);

    List<Map<String, Object>> selectTree();
}




