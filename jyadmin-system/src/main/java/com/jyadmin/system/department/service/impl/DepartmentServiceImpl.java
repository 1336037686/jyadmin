package com.jyadmin.system.department.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyadmin.system.department.domain.Department;
import com.jyadmin.system.department.mapper.DepartmentMapper;
import com.jyadmin.system.department.model.vo.DepartmentQueryVO;
import com.jyadmin.system.department.service.DepartmentService;
import com.jyadmin.util.DataUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author 13360
* @description 针对表【sys_department(部门表)】的数据库操作Service实现
* @createDate 2023-05-04 22:45:07
*/
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department>
    implements DepartmentService{

    @Resource
    private DepartmentMapper departmentMapper;

    @Override
    public List<Map<String, Object>> getLayer(DepartmentQueryVO vo) {
        List<Department> departments = this.getBaseMapper().selectList(
                new LambdaQueryWrapper<Department>()
                        .like(StringUtils.isNotBlank(vo.getName()), Department::getName, vo.getName())
                        .like(StringUtils.isNotBlank(vo.getCode()), Department::getCode, vo.getCode())
                        .eq(Objects.nonNull(vo.getIsRoot()), Department::getIsRoot, vo.getIsRoot())
                        .eq(Objects.nonNull(vo.getStatus()), Department::getStatus, vo.getStatus())
                        .orderByAsc(Department::getSort)
        );

        List<Map<String, Object>> deptMaps = departments.stream().map(BeanUtil::beanToMap)
                .peek(x -> {
                    // 将ID转换为String
                    x.put("id", DataUtil.getValueForMap(x, "id"));
                    x.put("parentId", DataUtil.getValueForMap(x, "parentId"));
                    // 格式化创建日期，避免前端日期问题
                    if (Objects.nonNull(x.get("createTime"))) x.put("createTime", ((LocalDateTime) x.get("createTime")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    // 剔除其他无用字段
                    DataUtil.removeItemForMap(x, "createBy", "updateBy", "updateTime", "deleted");
                }).collect(Collectors.toList());
        Set<String> childrenDepts = new HashSet<>();
        Map<String, Map<String, Object>> table = new HashMap<>();
        for (Map<String, Object> dept : deptMaps) table.put(dept.get("id").toString(), dept);
        for (Map<String, Object> dept : deptMaps) {
            Map<String, Object> parentDept = table.get(dept.get("parentId").toString());
            if (Objects.isNull(parentDept)) continue;
            List<Map<String, Object>> children = (List<Map<String, Object>>) parentDept.getOrDefault("children", new ArrayList<>());
            children.add(dept);
            parentDept.put("children", children);
            childrenDepts.add(dept.get("id").toString());
        }
        // 获取所有顶级节点
        return deptMaps.stream().filter(x -> !childrenDepts.contains(x.get("id").toString())).collect(Collectors.toList());
    }

}




