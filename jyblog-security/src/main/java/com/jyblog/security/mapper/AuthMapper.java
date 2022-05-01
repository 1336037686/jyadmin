package com.jyblog.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jyblog.security.domain.PermissionAction;
import com.jyblog.security.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
* @author 13360
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2022-04-12 23:19:40
* @Entity com.jyblog.system.user.domain.User
*/
public interface AuthMapper extends BaseMapper<User> {

    List<PermissionAction> selectPermissions(@Param("userId") String userId);

    List<Map<String, Object>> selectMenus(@Param("userId") String userId);

    List<Map<String, Object>> selectRoles(@Param("userId") String userId);
}




