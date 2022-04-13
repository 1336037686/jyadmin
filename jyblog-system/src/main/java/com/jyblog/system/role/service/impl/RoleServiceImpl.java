package com.jyblog.system.role.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyblog.system.role.domain.Role;
import com.jyblog.system.role.service.RoleService;
import com.jyblog.system.role.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
* @author 13360
* @description 针对表【sys_role(系统角色表)】的数据库操作Service实现
* @createDate 2022-04-13 23:22:16
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

}




