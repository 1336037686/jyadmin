package com.jyadmin.system.role.service;

import com.jyadmin.system.role.domain.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
* @author 13360
* @description 针对表【tr_user_role(系统用户角色中间表)】的数据库操作Service
* @createDate 2022-04-13 23:22:50
*/
public interface UserRoleService extends IService<UserRole> {

    boolean saveFromUser(Long userId, Set<Long> ids);
}
