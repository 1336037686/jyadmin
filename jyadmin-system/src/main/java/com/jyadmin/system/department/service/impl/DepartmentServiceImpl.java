package com.jyadmin.system.department.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyadmin.system.department.domain.Department;
import com.jyadmin.system.department.service.DepartmentService;
import com.jyadmin.system.department.mapper.DepartmentMapper;
import org.springframework.stereotype.Service;

/**
* @author 13360
* @description 针对表【sys_department(部门表)】的数据库操作Service实现
* @createDate 2023-05-04 22:45:07
*/
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department>
    implements DepartmentService{

}




