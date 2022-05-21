package com.jyadmin.system.role.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.system.role.model.vo.RoleCreateVO;
import com.jyadmin.system.role.model.vo.RoleQueryVO;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.system.role.domain.Role;
import com.jyadmin.system.role.domain.UserRole;
import com.jyadmin.system.role.model.vo.RoleUpdateVO;
import com.jyadmin.system.role.service.RoleService;
import com.jyadmin.system.role.service.UserRoleService;
import com.jyadmin.util.PageUtil;
import com.jyadmin.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统角色
 * @author LGX_TvT
 * @date 2022-04-14 15:24
 */
@Slf4j
@Api(value = "系统角色", tags = {"系统：系统角色接口"})
@RequestMapping("/api/role")
@RestController
public class RoleController {

    @Resource
    private RoleService roleService;

    @Resource
    private UserRoleService userRoleService;

    @ApiOperation(value = "新增角色", notes = "")
    @PostMapping("/create")
    public Result<Object> doCreate(@RequestBody @Valid RoleCreateVO vo) {
        return ResultUtil.toResult(roleService.save(BeanUtil.copyProperties(vo, Role.class)));
    }

    @ApiOperation(value = "更新角色", notes = "")
    @PutMapping("/update")
    public Result<Object> doUpdate(@RequestBody @Valid RoleUpdateVO vo) {
        Role role = roleService.getById(vo.getId());
        BeanUtil.copyProperties(vo, role);
        return ResultUtil.toResult(roleService.updateById(role));
    }

    @ApiOperation(value = "删除角色", notes = "")
    @DeleteMapping("/remove")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(roleService.removeByIds(ids));
    }

    @ApiOperation(value = "根据ID查找角色信息", notes = "")
    @GetMapping("/query/{id}")
    public Result<Object> doQueryById(@PathVariable String id) {
        return Result.ok(roleService.getById(id));
    }

    @ApiOperation(value = "列表查询角色信息", notes = "")
    @GetMapping("/list")
    public Result<List<Role>> doQueryList(RoleQueryVO vo) {
        return Result.ok(this.roleService.getBaseMapper().selectList(
                        new LambdaQueryWrapper<Role>()
                                .like(StringUtils.isNotBlank(vo.getName()), Role::getName, vo.getName())
                                .like(StringUtils.isNotBlank(vo.getCode()), Role::getCode, vo.getCode())
                                .eq(Objects.nonNull(vo.getStatus()), Role::getStatus, vo.getStatus())
                )
        );
    }

    @ApiOperation(value = "分页查询角色信息", notes = "")
    @GetMapping("/query")
    public PageResult<Role> doQueryPage(RoleQueryVO vo) {
        return PageUtil.toPageResult(
                this.roleService.page(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                        new LambdaQueryWrapper<Role>()
                        .like(StringUtils.isNotBlank(vo.getName()), Role::getName, vo.getName())
                        .like(StringUtils.isNotBlank(vo.getCode()), Role::getCode, vo.getCode())
                        .eq(Objects.nonNull(vo.getStatus()), Role::getStatus, vo.getStatus())
                )
        );
    }

    @ApiOperation(value = "创建用户角色", notes = "")
    @PostMapping("/create/user/{userId}")
    public Result<Object> doCreateFromUser(@PathVariable("userId") String userId, @RequestBody Set<String> ids) {
        return ResultUtil.toResult(roleService.saveFromUser(userId, ids));
    }

    @ApiOperation(value = "获取用户角色", notes = "")
    @GetMapping("/query/user/{userId}")
    public Result<List<String>> doQueryFromUser(@PathVariable("userId") String userId) {
        List<UserRole> roles = userRoleService.getBaseMapper().selectList(
                new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId)
        );
        List<String> roleIds = roles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        return Result.ok(roleIds);
    }
}
