package com.jyadmin.system.department.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyadmin.system.department.domain.UserDepartment;
import com.jyadmin.system.department.service.UserDepartmentService;
import com.jyadmin.system.department.mapper.UserDepartmentMapper;
import org.springframework.stereotype.Service;

/**
* @author 13360
* @description 针对表【tr_user_department(系统用户角色中间表)】的数据库操作Service实现
* @createDate 2023-05-04 22:46:24
*/
@Service
public class UserDepartmentServiceImpl extends ServiceImpl<UserDepartmentMapper, UserDepartment>
    implements UserDepartmentService{

}




