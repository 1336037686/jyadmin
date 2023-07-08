package com.jyadmin.system.permission.action.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Sets;
import com.jyadmin.annotation.RateLimit;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.log.annotation.Log;
import com.jyadmin.system.permission.action.domain.PermissionAction;
import com.jyadmin.system.permission.action.domain.PermissionMenuAction;
import com.jyadmin.system.permission.action.model.vo.PermissionActionCreateVO;
import com.jyadmin.system.permission.action.model.vo.PermissionActionQueryVO;
import com.jyadmin.system.permission.action.model.vo.PermissionActionUpdateVO;
import com.jyadmin.system.permission.action.service.PermissionActionService;
import com.jyadmin.system.permission.action.service.PermissionMenuActionService;
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
 * @date 2022-04-14 15:57
 */
@Slf4j
@Api(value = "系统权限动作", tags = {"系统：系统权限动作接口"})
@RequestMapping("/api/permission/action")
@RestController
public class PermissionActionController {

    @Resource
    private PermissionActionService permissionActionService;

    @Resource
    private PermissionMenuActionService permissionMenuActionService;

    @RateLimit
    @Log(title = "系统管理：新增权限动作", desc = "新增权限动作")
    @ApiOperation(value = "新增权限动作", notes = "")
    @PostMapping("/create")
    @PreAuthorize("@jy.check('action:create')")
    public Result<Object> doCreate(@RequestBody @Valid PermissionActionCreateVO vo) {
        return ResultUtil.toResult(permissionActionService.save(BeanUtil.copyProperties(vo, PermissionAction.class)));
    }

    @RateLimit
    @Log(title = "系统管理：更新权限动作", desc = "更新权限动作")
    @ApiOperation(value = "更新权限动作", notes = "")
    @PutMapping("/update")
    @PreAuthorize("@jy.check('action:update')")
    public Result<Object> doUpdate(@RequestBody @Valid PermissionActionUpdateVO vo) {
        PermissionAction permissionAction = permissionActionService.getById(vo.getId());
        BeanUtil.copyProperties(vo, permissionAction);
        return ResultUtil.toResult(permissionActionService.updateById(permissionAction));
    }

    @RateLimit
    @Log(title = "系统管理：删除权限动作", desc = "删除权限动作")
    @ApiOperation(value = "删除权限动作", notes = "")
    @DeleteMapping("/remove")
    @PreAuthorize("@jy.check('action:remove')")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(permissionActionService.removeByIds(DataUtil.convertToLongForSet(ids)));
    }

    @ApiOperation(value = "根据ID查找权限动作信息", notes = "")
    @GetMapping("/query/{id}")
    @PreAuthorize("@jy.check('action:queryById')")
    public Result<Object> doQueryById(@PathVariable String id) {
        return Result.ok(permissionActionService.getById(Long.parseLong(id)));
    }

    @ApiOperation(value = "查询权限动作树形数据", notes = "")
    @GetMapping("/tree")
    @PreAuthorize("@jy.check('action:tree')")
    public Result<List<Map<String, Object>>> doQueryTree() {
        return Result.ok(this.permissionActionService.getTree());
    }

    @ApiOperation(value = "列表查询权限动作信息", notes = "")
    @GetMapping("/list")
    @PreAuthorize("@jy.check('action:list')")
    public Result<List<PermissionAction>> doQueryList(PermissionActionQueryVO vo) {
        return Result.ok(this.permissionActionService.getBaseMapper().selectList(buildPermissionActionQueryWrapper(vo)));
    }

    @ApiOperation(value = "分页查询权限动作信息", notes = "")
    @GetMapping("/query")
    @PreAuthorize("@jy.check('action:query')")
    public PageResult<PermissionAction> doQueryPage(PermissionActionQueryVO vo) {
        return PageUtil.toPageResult(
                this.permissionActionService.page(
                        new Page<>(vo.getPageNumber(), vo.getPageSize()),
                        buildPermissionActionQueryWrapper(vo)
                )
        );
    }

    private LambdaQueryWrapper<PermissionAction> buildPermissionActionQueryWrapper(PermissionActionQueryVO vo) {
        return new LambdaQueryWrapper<PermissionAction>()
                .like(StringUtils.isNotBlank(vo.getName()), PermissionAction::getName, vo.getName())
                .like(StringUtils.isNotBlank(vo.getCode()), PermissionAction::getCode, vo.getCode())
                .eq(Objects.nonNull(vo.getGroupId()), PermissionAction::getGroupId, vo.getGroupId())
                .eq(Objects.nonNull(vo.getStatus()), PermissionAction::getStatus, vo.getStatus())
                .orderByAsc(PermissionAction::getSort);
    }

    @Log(title = "系统管理：创建菜单权限", desc = "创建菜单权限")
    @ApiOperation(value = "创建菜单权限", notes = "")
    @PostMapping("/create/menu/{menuId}")
    @PreAuthorize("@jy.check('action:createFromMenu')")
    public Result<Object> doCreateFromMenu(@PathVariable("menuId") String menuId, @RequestBody Set<String> ids) {
        Set<Long> newIds = CollectionUtils.isEmpty(ids) ? Sets.newHashSet() : ids.stream().map(Long::parseLong).collect(Collectors.toSet());
        return ResultUtil.toResult(permissionActionService.saveFromMenu(Long.parseLong(menuId), newIds));
    }

    @ApiOperation(value = "获取菜单权限", notes = "")
    @GetMapping("/query/menu/{menuId}")
    @PreAuthorize("@jy.check('action:queryFromMenu')")
    public Result<List<String>> doQueryFromMenu(@PathVariable("menuId") String menuId) {
        List<PermissionMenuAction> permissionMenuActions = permissionMenuActionService.getBaseMapper().selectList(
                new LambdaQueryWrapper<PermissionMenuAction>().eq(PermissionMenuAction::getMenuId, Long.parseLong(menuId))
        );
        List<String> actionIds = permissionMenuActions.stream().map(PermissionMenuAction::getActionId)
                .map(Objects::toString).collect(Collectors.toList());
        return Result.ok(actionIds);
    }

    @ApiOperation(value = "获取用户权限", notes = "")
    @GetMapping("/query/user/{userId}")
    @PreAuthorize("@jy.check('action:queryFromUser')")
    public Result<List<PermissionAction>> doQueryFromUser(@PathVariable("userId") String userId) {
        List<PermissionAction> permissionActions = this.permissionActionService.getFromUser(Long.parseLong(userId));
        return Result.ok(permissionActions);
    }


}
