package com.jyblog.system.permission.menu.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyblog.domain.PageResult;
import com.jyblog.domain.Result;
import com.jyblog.system.permission.menu.domain.PermissionMenu;
import com.jyblog.system.permission.menu.domain.PermissionRoleMenu;
import com.jyblog.system.permission.menu.model.vo.PermissionMenuCreateVO;
import com.jyblog.system.permission.menu.model.vo.PermissionMenuQueryVO;
import com.jyblog.system.permission.menu.model.vo.PermissionMenuUpdateVO;
import com.jyblog.system.permission.menu.service.PermissionMenuService;
import com.jyblog.system.permission.menu.service.PermissionRoleMenuService;
import com.jyblog.util.PageUtil;
import com.jyblog.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
@Api(value = "系统权限菜单", tags = {"系统权限菜单接口"})
@RequestMapping("permission/menu")
@RestController
public class PermissionMenuController {

    @Resource
    private PermissionMenuService permissionMenuService;

    @Resource
    private PermissionRoleMenuService permissionRoleMenuService;

    @ApiOperation(value = "新增菜单", notes = "")
    @PostMapping("/create")
    public Result<Object> doCreate(@RequestBody @Valid PermissionMenuCreateVO vo) {
        return ResultUtil.toResult(permissionMenuService.save(BeanUtil.copyProperties(vo, PermissionMenu.class)));
    }

    @ApiOperation(value = "更新菜单", notes = "")
    @PutMapping("/update")
    public Result<Object> doUpdate(@RequestBody @Valid PermissionMenuUpdateVO vo) {
        PermissionMenu permissionMenu = permissionMenuService.getById(vo.getId());
        BeanUtil.copyProperties(vo, permissionMenu);
        return ResultUtil.toResult(permissionMenuService.updateById(permissionMenu));
    }

    @ApiOperation(value = "删除菜单", notes = "")
    @DeleteMapping("/remove")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(permissionMenuService.removeByIds(ids));
    }

    @ApiOperation(value = "根据ID查找菜单信息", notes = "")
    @GetMapping("/query/{id}")
    public Result<Object> doQueryById(@PathVariable String id) {
        return Result.ok(permissionMenuService.getById(id));
    }

    @ApiOperation(value = "列表查询菜单信息", notes = "")
    @GetMapping("/list")
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

    @ApiOperation(value = "分页查询菜单信息", notes = "")
    @GetMapping("/query")
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

    @ApiOperation(value = "创建角色菜单", notes = "")
    @PostMapping("/create/role/{roleId}")
    public Result<Object> doCreateFromRole(@PathVariable("roleId") String roleId, @RequestBody Set<String> ids) {
        return ResultUtil.toResult(permissionMenuService.saveFromRole(roleId, ids));
    }

    @ApiOperation(value = "获取角色菜单", notes = "")
    @GetMapping("/query/role/{roleId}")
    public Result<List<String>> doQueryFromRole(@PathVariable("roleId") String roleId) {
        List<PermissionRoleMenu> roles = permissionRoleMenuService.getBaseMapper().selectList(
                new LambdaQueryWrapper<PermissionRoleMenu>().eq(PermissionRoleMenu::getRoleId, roleId)
        );
        List<String> menuIds = roles.stream().map(PermissionRoleMenu::getMenuId).collect(Collectors.toList());
        return Result.ok(menuIds);
    }


    @ApiOperation(value = "获取用户菜单", notes = "")
    @GetMapping("/query/user/{userId}")
    public Result<List<Map<String, Object>>> doQueryFromUser(@PathVariable("userId") String userId) {
        List<Map<String, Object>> menus = this.permissionMenuService.getFromUser(userId);
        return Result.ok(menus);
    }
}
