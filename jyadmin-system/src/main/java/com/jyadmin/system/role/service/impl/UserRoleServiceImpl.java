package com.jyadmin.system.role.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyadmin.system.role.domain.UserRole;
import com.jyadmin.system.role.mapper.UserRoleMapper;
import com.jyadmin.system.role.service.UserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author 13360
* @description 针对表【tr_user_role(系统用户角色中间表)】的数据库操作Service实现
* @createDate 2022-04-13 23:22:50
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Resource
    private UserRoleService userRoleService;

    @Override
    public boolean saveFromUser(Long userId, Set<Long> ids) {
        userRoleService.remove(
                new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId)
        );
        List<UserRole> userRoles = ids.stream()
                .map(x -> new UserRole().setUserId(userId).setRoleId(x))
                .collect(Collectors.toList());
        userRoleService.saveBatch(userRoles);
        return true;
    }
}




