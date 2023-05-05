package com.jyadmin.system.department.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jyadmin.system.department.domain.Department;
import com.jyadmin.system.department.model.vo.DepartmentQueryVO;

import java.util.List;
import java.util.Map;

/**
* @author 13360
* @description 针对表【sys_department(部门表)】的数据库操作Service
* @createDate 2023-05-04 22:45:07
*/
public interface DepartmentService extends IService<Department> {

    List<Map<String, Object>> getLayer(DepartmentQueryVO vo);

}
