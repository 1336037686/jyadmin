package com.jyblog.system.role.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyblog.system.role.domain.UserRole;
import com.jyblog.system.role.service.UserRoleService;
import com.jyblog.system.role.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author 13360
* @description 针对表【tr_user_role(系统用户角色中间表)】的数据库操作Service实现
* @createDate 2022-04-13 23:22:50
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




