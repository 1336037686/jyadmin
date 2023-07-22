package com.jyadmin.system.role.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.annotation.RateLimit;
import com.jyadmin.consts.GlobalConstants;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.log.annotation.Log;
import com.jyadmin.system.role.domain.Role;
import com.jyadmin.system.role.domain.UserRole;
import com.jyadmin.system.role.model.vo.RoleCreateVO;
import com.jyadmin.system.role.model.vo.RoleQueryVO;
import com.jyadmin.system.role.model.vo.RoleUpdateVO;
import com.jyadmin.system.role.service.RoleService;
import com.jyadmin.system.role.service.UserRoleService;
import com.jyadmin.util.DataUtil;
import com.jyadmin.util.PageUtil;
import com.jyadmin.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @RateLimit
    @Log(title = "系统管理：新增角色", desc = "新增角色")
    @ApiOperation(value = "新增角色", notes = "")
    @PostMapping("/create")
    @PreAuthorize("@jy.check('role:create')")
    public Result<Object> doCreate(@RequestBody @Valid RoleCreateVO vo) {
        return ResultUtil.toResult(roleService.save(BeanUtil.copyProperties(vo, Role.class)));
    }

    @RateLimit
    @Log(title = "系统管理：更新角色", desc = "更新角色")
    @ApiOperation(value = "更新角色", notes = "")
    @PutMapping("/update")
    @PreAuthorize("@jy.check('role:update')")
    public Result<Object> doUpdate(@RequestBody @Valid RoleUpdateVO vo) {
        Role role = roleService.getById(vo.getId());
        BeanUtil.copyProperties(vo, role);
        // 如果不是自定义范围，则清空自定义数据范围
        if (!GlobalConstants.SYS_ROLE_DATA_SCOPE_OTHER.equals(role.getDataScope())) role.setUserDefineDataScope(null);
        return ResultUtil.toResult(roleService.saveOrUpdate(role));
    }

    @RateLimit
    @Log(title = "系统管理：删除角色", desc = "删除角色")
    @ApiOperation(value = "删除角色", notes = "")
    @DeleteMapping("/remove")
    @PreAuthorize("@jy.check('role:remove')")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(roleService.removeByIds(DataUtil.convertToLongForSet(ids)));
    }

    @ApiOperation(value = "根据ID查找角色信息", notes = "")
    @GetMapping("/query/{id}")
    @PreAuthorize("@jy.check('role:queryById')")
    public Result<Object> doQueryById(@PathVariable String id) {
        return Result.ok(roleService.getById(Long.parseLong(id)));
    }

    @ApiOperation(value = "列表查询角色信息", notes = "")
    @GetMapping("/list")
    @PreAuthorize("@jy.check('role:list')")
    public Result<List<Role>> doQueryList(RoleQueryVO vo) {
        return Result.ok(this.roleService.getBaseMapper().selectList(
                        new LambdaQueryWrapper<Role>()
                                .like(StringUtils.isNotBlank(vo.getName()), Role::getName, vo.getName())
                                .like(StringUtils.isNotBlank(vo.getCode()), Role::getCode, vo.getCode())
                                .eq(Objects.nonNull(vo.getStatus()), Role::getStatus, vo.getStatus())
                                .orderByAsc(Role::getSort)
                )
        );
    }

    @ApiOperation(value = "分页查询角色信息", notes = "")
    @GetMapping("/query")
    @PreAuthorize("@jy.check('role:query')")
    public PageResult<Role> doQueryPage(RoleQueryVO vo) {
        return PageUtil.toPageResult(
                this.roleService.page(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                        new LambdaQueryWrapper<Role>()
                        .like(StringUtils.isNotBlank(vo.getName()), Role::getName, vo.getName())
                        .like(StringUtils.isNotBlank(vo.getCode()), Role::getCode, vo.getCode())
                        .eq(Objects.nonNull(vo.getStatus()), Role::getStatus, vo.getStatus())
                        .orderByAsc(Role::getSort)
                )
        );
    }

    @RateLimit
    @Log(title = "系统管理：创建用户角色", desc = "创建用户角色")
    @ApiOperation(value = "创建用户角色", notes = "")
    @PostMapping("/create/user/{userId}")
    @PreAuthorize("@jy.check('role:createFromUser')")
    public Result<Object> doCreateFromUser(@PathVariable("userId") String userId, @RequestBody Set<String> ids) {
        return ResultUtil.toResult(roleService.saveFromUser(Long.parseLong(userId), DataUtil.convertToLongForSet(ids)));
    }

    @ApiOperation(value = "获取用户角色", notes = "")
    @GetMapping("/query/user/{userId}")
    @PreAuthorize("@jy.check('role:queryFromUser')")
    public Result<List<String>> doQueryFromUser(@PathVariable("userId") String userId) {
        List<UserRole> roles = userRoleService.getBaseMapper().selectList(
                new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, Long.parseLong(userId))
        );
        List<String> roleIds = roles.stream().map(UserRole::getRoleId).map(Objects::toString).collect(Collectors.toList());
        return Result.ok(roleIds);
    }
}
