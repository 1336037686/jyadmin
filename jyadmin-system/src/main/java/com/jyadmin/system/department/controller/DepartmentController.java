package com.jyadmin.system.department.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.annotation.RateLimit;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.log.annotation.Log;
import com.jyadmin.system.department.domain.Department;
import com.jyadmin.system.department.model.vo.DepartmentCreateVO;
import com.jyadmin.system.department.model.vo.DepartmentQueryVO;
import com.jyadmin.system.department.model.vo.DepartmentUpdateVO;
import com.jyadmin.system.department.service.DepartmentService;
import com.jyadmin.util.DataUtil;
import com.jyadmin.util.PageUtil;
import com.jyadmin.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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

    @RateLimit
    @Log(title = "部门管理：新增部门", desc = "新增部门")
    @ApiOperation(value = "新增部门", notes = "")
    @PostMapping("/create")
    @PreAuthorize("@jy.check('department:create')")
    public Result<Object> doCreate(@RequestBody @Valid DepartmentCreateVO vo) {
        return ResultUtil.toResult(departmentService.save(BeanUtil.copyProperties(vo, Department.class)));
    }

    @RateLimit
    @Log(title = "部门管理：更新部门", desc = "更新部门")
    @ApiOperation(value = "更新部门", notes = "")
    @PutMapping("/update")
    @PreAuthorize("@jy.check('department:update')")
    public Result<Object> doUpdate(@RequestBody @Valid DepartmentUpdateVO vo) {
        Department department = departmentService.getById(vo.getId());
        BeanUtil.copyProperties(vo, department);
        return ResultUtil.toResult(departmentService.updateById(department));
    }

    @RateLimit
    @Log(title = "部门管理：删除部门", desc = "删除部门")
    @ApiOperation(value = "删除部门", notes = "")
    @DeleteMapping("/remove")
    @PreAuthorize("@jy.check('department:remove')")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(departmentService.removeByIds(DataUtil.convertToLongForSet(ids)));
    }

    @ApiOperation(value = "根据ID获取当前部门信息", notes = "")
    @GetMapping("/query/{id}")
    @PreAuthorize("@jy.check('department:queryById')")
    public Result<Object> doQueryById(@PathVariable("id") String id) {
        return Result.ok(departmentService.getById(Long.parseLong(id)));
    }

    @ApiOperation(value = "分页查询部门", notes = "")
    @GetMapping("/query")
    @PreAuthorize("@jy.check('department:query')")
    public PageResult<Department> doQueryPage(DepartmentQueryVO vo) {
        return PageUtil.toPageResult(
                this.departmentService.page(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                        new LambdaQueryWrapper<Department>()
                                .like(StringUtils.isNotBlank(vo.getName()), Department::getName, vo.getName())
                                .like(StringUtils.isNotBlank(vo.getCode()), Department::getCode, vo.getCode())
                                .eq(Objects.nonNull(vo.getIsRoot()), Department::getIsRoot, vo.getIsRoot())
                                .orderByAsc(Department::getSort)
                )
        );
    }

    @ApiOperation(value = "层次列表查询部门信息", notes = "")
    @GetMapping("/layer")
    @PreAuthorize("@jy.check('department:layer')")
    public Result<List<Map<String, Object>>> doQueryLayer(DepartmentQueryVO vo) {
        return Result.ok(this.departmentService.getLayer(vo));
    }

}
