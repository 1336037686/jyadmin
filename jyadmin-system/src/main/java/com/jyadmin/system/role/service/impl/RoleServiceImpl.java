package com.jyadmin.system.role.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyadmin.system.role.domain.Role;
import com.jyadmin.system.role.domain.UserRole;
import com.jyadmin.system.role.mapper.RoleMapper;
import com.jyadmin.system.role.service.RoleService;
import com.jyadmin.system.role.service.UserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author 13360
* @description 针对表【sys_role(系统角色表)】的数据库操作Service实现
* @createDate 2022-04-13 23:22:16
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private UserRoleService userRoleService;

    @Override
    public boolean saveFromUser(Long userId, Set<Long> ids) {
        this.userRoleService.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId));
        List<UserRole> userRoles = ids.stream().map(x -> new UserRole().setUserId(userId).setRoleId(x)).collect(Collectors.toList());
        userRoleService.saveBatch(userRoles);
        return true;
    }
}




