package com.jyadmin.system.department.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.system.department.domain.Department;
import com.jyadmin.system.department.model.vo.DepartmentCreateVO;
import com.jyadmin.system.department.model.vo.DepartmentQueryVO;
import com.jyadmin.system.department.model.vo.DepartmentUpdateVO;
import com.jyadmin.system.department.service.DepartmentService;
import com.jyadmin.util.PageUtil;
import com.jyadmin.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Set;

/**
 * 部门管理
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-05-05 00:00 <br>
 * @description: DepartmentController <br>
 */
@Slf4j
@Api(value = "部门管理", tags = {"系统：部门管理接口"})
@RequestMapping("/api/department")
@RestController
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;

    @ApiOperation(value = "新增部门", notes = "")
    @PostMapping("/create")
    public Result<Object> doCreate(@RequestBody @Valid DepartmentCreateVO vo) {
        return ResultUtil.toResult(departmentService.save(BeanUtil.copyProperties(vo, Department.class)));
    }

    @ApiOperation(value = "更新部门", notes = "")
    @PutMapping("/update")
    public Result<Object> doUpdate(@RequestBody @Valid DepartmentUpdateVO vo) {
        Department department = departmentService.getById(vo.getId());
        BeanUtil.copyProperties(vo, department);
        return ResultUtil.toResult(departmentService.updateById(department));
    }

    @ApiOperation(value = "删除部门", notes = "")
    @DeleteMapping("/remove")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(departmentService.removeByIds(ids));
    }

    @ApiOperation(value = "根据ID获取当前部门信息", notes = "")
    @GetMapping("/query/{id}")
    public Result<Object> doQueryById(@PathVariable String id) {
        return Result.ok(departmentService.getById(id));
    }

    @ApiOperation(value = "分页查询部门", notes = "")
    @GetMapping("/query")
    public PageResult<Department> doQueryPage(DepartmentQueryVO vo) {
        return PageUtil.toPageResult(
                this.departmentService.page(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                        new LambdaQueryWrapper<Department>()
                                .like(StringUtils.isNotBlank(vo.getName()), Department::getName, vo.getName())
                                .like(StringUtils.isNotBlank(vo.getCode()), Department::getCode, vo.getCode())
                )
        );
    }
    
}
