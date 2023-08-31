package com.jyadmin.system.permission.menu.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Sets;
import com.jyadmin.annotation.RateLimit;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.log.annotation.Log;
import com.jyadmin.system.permission.menu.domain.PermissionMenu;
import com.jyadmin.system.permission.menu.domain.PermissionRoleMenu;
import com.jyadmin.system.permission.menu.model.vo.PermissionMenuCreateVO;
import com.jyadmin.system.permission.menu.model.vo.PermissionMenuQueryVO;
import com.jyadmin.system.permission.menu.model.vo.PermissionMenuUpdateVO;
import com.jyadmin.system.permission.menu.service.PermissionMenuService;
import com.jyadmin.system.permission.menu.service.PermissionRoleMenuService;
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
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author LGX_TvT
 * @date 2022-04-14 15:58
 */
@Slf4j
@Api(value = "系统权限菜单", tags = {"系统：系统权限菜单接口"})
@RequestMapping("/api/permission/menu")
@RestController
public class PermissionMenuController {

    @Resource
    private PermissionMenuService permissionMenuService;

    @Resource
    private PermissionRoleMenuService permissionRoleMenuService;

    @RateLimit
    @Log(title = "系统管理：新增菜单", desc = "新增菜单")
    @ApiOperation(value = "新增菜单", notes = "")
    @PostMapping("/create")
    @PreAuthorize("@jy.check('menu:create')")
    public Result<Object> doCreate(@RequestBody @Valid PermissionMenuCreateVO vo) {
        return ResultUtil.toResult(permissionMenuService.save(BeanUtil.copyProperties(vo, PermissionMenu.class)));
    }

    @RateLimit
    @Log(title = "系统管理：更新菜单", desc = "更新菜单")
    @ApiOperation(value = "更新菜单", notes = "")
    @PutMapping("/update")
    @PreAuthorize("@jy.check('menu:update')")
    public Result<Object> doUpdate(@RequestBody @Valid PermissionMenuUpdateVO vo) {
        PermissionMenu permissionMenu = permissionMenuService.getById(vo.getId());
        BeanUtil.copyProperties(vo, permissionMenu);
        return ResultUtil.toResult(permissionMenuService.updateById(permissionMenu));
    }

    @RateLimit
    @Log(title = "系统管理：删除菜单", desc = "删除菜单")
    @ApiOperation(value = "删除菜单", notes = "")
    @DeleteMapping("/remove")
    @PreAuthorize("@jy.check('menu:remove')")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(permissionMenuService.removeByIds(DataUtil.convertToLongForSet(ids)));
    }

    @ApiOperation(value = "根据ID查找菜单信息", notes = "")
    @GetMapping("/query/{id}")
    @PreAuthorize("@jy.check('menu:queryById')")
    public Result<Object> doQueryById(@PathVariable String id) {
        return Result.ok(permissionMenuService.getById(Long.parseLong(id)));
    }

    @ApiOperation(value = "列表查询菜单信息", notes = "")
    @GetMapping("/list")
    @PreAuthorize("@jy.check('menu:list')")
    public Result<List<PermissionMenu>> doQueryList(PermissionMenuQueryVO vo) {
        return Result.ok(
                this.permissionMenuService.getBaseMapper().selectList(
                        new LambdaQueryWrapper<PermissionMenu>()
                                .like(StringUtils.isNotBlank(vo.getName()), PermissionMenu::getName, vo.getName())
                                .like(StringUtils.isNotBlank(vo.getCode()), PermissionMenu::getCode, vo.getCode())
                                .eq(Objects.nonNull(vo.getType()), PermissionMenu::getType, vo.getType())
                                .eq(Objects.nonNull(vo.getParentId()), PermissionMenu::getParentId, vo.getParentId())
                                .like(StringUtils.isNotBlank(vo.getUrl()), PermissionMenu::getUrl, vo.getUrl())
                                .like(StringUtils.isNotBlank(vo.getPath()), PermissionMenu::getPath, vo.getPath())
                                .eq(Objects.nonNull(vo.getCache()), PermissionMenu::getCache, vo.getCache())
                                .eq(Objects.nonNull(vo.getVisiable()), PermissionMenu::getVisiable, vo.getVisiable())
                                .eq(Objects.nonNull(vo.getLink()), PermissionMenu::getLink, vo.getLink())
                                .eq(Objects.nonNull(vo.getStatus()), PermissionMenu::getStatus, vo.getStatus())
                )
        );
    }

    @ApiOperation(value = "层次列表查询菜单信息", notes = "")
    @GetMapping("/layer")
    @PreAuthorize("@jy.check('menu:layer')")
    public Result<List<Map<String, Object>>> doQueryLayer(PermissionMenuQueryVO vo) {
        return Result.ok(this.permissionMenuService.getLayer(vo));
    }

    @ApiOperation(value = "分页查询菜单信息", notes = "")
    @GetMapping("/query")
    @PreAuthorize("@jy.check('menu:query')")
    public PageResult<PermissionMenu> doQueryPage(PermissionMenuQueryVO vo) {
        return PageUtil.toPageResult(
                this.permissionMenuService.page(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                        new LambdaQueryWrapper<PermissionMenu>()
                                .like(StringUtils.isNotBlank(vo.getName()), PermissionMenu::getName, vo.getName())
                                .like(StringUtils.isNotBlank(vo.getCode()), PermissionMenu::getCode, vo.getCode())
                                .eq(Objects.nonNull(vo.getType()), PermissionMenu::getType, vo.getType())
                                .eq(Objects.nonNull(vo.getParentId()), PermissionMenu::getParentId, vo.getParentId())
                                .like(StringUtils.isNotBlank(vo.getUrl()), PermissionMenu::getUrl, vo.getUrl())
                                .like(StringUtils.isNotBlank(vo.getPath()), PermissionMenu::getPath, vo.getPath())
                                .eq(Objects.nonNull(vo.getCache()), PermissionMenu::getCache, vo.getCache())
                                .eq(Objects.nonNull(vo.getVisiable()), PermissionMenu::getVisiable, vo.getVisiable())
                                .eq(Objects.nonNull(vo.getLink()), PermissionMenu::getLink, vo.getLink())
                                .eq(Objects.nonNull(vo.getStatus()), PermissionMenu::getStatus, vo.getStatus())
                )
        );
    }

    @RateLimit
    @Log(title = "系统管理：创建角色菜单", desc = "创建角色菜单")
    @ApiOperation(value = "创建角色菜单", notes = "")
    @PostMapping("/create/role/{roleId}")
    @PreAuthorize("@jy.check('menu:createFromRole')")
    public Result<Object> doCreateFromRole(@PathVariable("roleId") String roleId, @RequestBody Set<String> ids) {
        Set<Long> newIds = CollectionUtils.isEmpty(ids) ? Sets.newHashSet() : ids.stream().map(Long::parseLong).collect(Collectors.toSet());
        return ResultUtil.toResult(permissionMenuService.saveFromRole(Long.parseLong(roleId), newIds));
    }

    @ApiOperation(value = "获取角色菜单", notes = "")
    @GetMapping("/query/role/{roleId}")
    @PreAuthorize("@jy.check('menu:queryFromRole')")
    public Result<List<String>> doQueryFromRole(@PathVariable("roleId") String roleId) {
        List<PermissionRoleMenu> roles = permissionRoleMenuService.getBaseMapper().selectList(
                new LambdaQueryWrapper<PermissionRoleMenu>().eq(PermissionRoleMenu::getRoleId, Long.parseLong(roleId))
        );
        List<String> menuIds = roles.stream().map(PermissionRoleMenu::getMenuId).map(Objects::toString).collect(Collectors.toList());
        return Result.ok(menuIds);
    }

    @ApiOperation(value = "获取用户菜单", notes = "")
    @GetMapping("/query/user/{userId}")
    @PreAuthorize("@jy.check('menu:queryFromUser')")
    public Result<List<Map<String, Object>>> doQueryFromUser(@PathVariable("userId") String userId) {
        List<Map<String, Object>> menus = this.permissionMenuService.getFromUser(Long.parseLong(userId));
        return Result.ok(menus);
    }
}
