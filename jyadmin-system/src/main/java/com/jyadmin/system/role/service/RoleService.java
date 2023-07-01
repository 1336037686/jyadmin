package com.jyadmin.system.role.service;

import com.jyadmin.system.role.domain.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
* @author 13360
* @description 针对表【sys_role(系统角色表)】的数据库操作Service
* @createDate 2022-04-13 23:22:16
*/
public interface RoleService extends IService<Role> {

    boolean saveFromUser(Long userId, Set<Long> ids);
}
