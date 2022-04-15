package com.jyblog.system.permission.action.mapper;

import com.jyblog.system.permission.action.domain.PermissionAction;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 13360
* @description 针对表【sys_permission_action(系统权限动作表)】的数据库操作Mapper
* @createDate 2022-04-13 23:20:54
* @Entity com.jyblog.system.permission.action.domain.PermissionAction
*/
@Mapper
public interface PermissionActionMapper extends BaseMapper<PermissionAction> {

    List<PermissionAction> selectFromUser(String userId);
}




