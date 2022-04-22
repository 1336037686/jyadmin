package com.jyblog.system.permission.action.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyblog.domain.PageResult;
import com.jyblog.domain.Result;
import com.jyblog.system.permission.action.domain.PermissionAction;
import com.jyblog.system.permission.action.domain.PermissionMenuAction;
import com.jyblog.system.permission.action.model.vo.PermissionActionCreateVO;
import com.jyblog.system.permission.action.model.vo.PermissionActionQueryVO;
import com.jyblog.system.permission.action.model.vo.PermissionActionUpdateVO;
import com.jyblog.system.permission.action.service.PermissionActionService;
import com.jyblog.system.permission.action.service.PermissionMenuActionService;
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

    @ApiOperation(value = "新增权限动作", notes = "")
    @PostMapping("/create")
    public Result<Object> doCreate(@RequestBody @Valid PermissionActionCreateVO vo) {
        return ResultUtil.toResult(permissionActionService.save(BeanUtil.copyProperties(vo, PermissionAction.class)));
    }

    @ApiOperation(value = "更新权限动作", notes = "")
    @PutMapping("/update")
    public Result<Object> doUpdate(@RequestBody @Valid PermissionActionUpdateVO vo) {
        PermissionAction permissionAction = permissionActionService.getById(vo.getId());
        BeanUtil.copyProperties(vo, permissionAction);
        return ResultUtil.toResult(permissionActionService.updateById(permissionAction));
    }

    @ApiOperation(value = "删除权限动作", notes = "")
    @DeleteMapping("/remove")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(permissionActionService.removeByIds(ids));
    }

    @ApiOperation(value = "根据ID查找权限动作信息", notes = "")
    @GetMapping("/query/{id}")
    public Result<Object> doQueryById(@PathVariable String id) {
        return Result.ok(permissionActionService.getById(id));
    }

    @ApiOperation(value = "列表查询权限动作信息", notes = "")
    @GetMapping("/list")
    public Result<List<PermissionAction>> doQueryList(PermissionActionQueryVO vo) {
        return Result.ok(
                this.permissionActionService.getBaseMapper().selectList(
                        new LambdaQueryWrapper<PermissionAction>()
                                .like(StringUtils.isNotBlank(vo.getName()), PermissionAction::getName, vo.getName())
                                .like(StringUtils.isNotBlank(vo.getCode()), PermissionAction::getCode, vo.getCode())
                                .eq(Objects.nonNull(vo.getGroupId()), PermissionAction::getGroupId, vo.getGroupId())
                                .eq(Objects.nonNull(vo.getStatus()), PermissionAction::getStatus, vo.getStatus())
                )
        );
    }

    @ApiOperation(value = "分页查询权限动作信息", notes = "")
    @GetMapping("/query")
    public PageResult<PermissionAction> doQueryPage(PermissionActionQueryVO vo) {
        return PageUtil.toPageResult(
                this.permissionActionService.page(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                        new LambdaQueryWrapper<PermissionAction>()
                                .like(StringUtils.isNotBlank(vo.getName()), PermissionAction::getName, vo.getName())
                                .like(StringUtils.isNotBlank(vo.getCode()), PermissionAction::getCode, vo.getCode())
                                .eq(Objects.nonNull(vo.getGroupId()), PermissionAction::getGroupId, vo.getGroupId())
                                .eq(Objects.nonNull(vo.getStatus()), PermissionAction::getStatus, vo.getStatus())
                )
        );
    }

    @ApiOperation(value = "创建菜单权限", notes = "")
    @PostMapping("/create/menu/{menuId}")
    public Result<Object> doCreateFromMenu(@PathVariable("menuId") String menuId, @RequestBody Set<String> ids) {
        return ResultUtil.toResult(permissionActionService.saveFromMenu(menuId, ids));
    }

    @ApiOperation(value = "获取菜单权限", notes = "")
    @GetMapping("/query/menu/{menuId}")
    public Result<List<String>> doQueryFromMenu(@PathVariable("menuId") String menuId) {
        List<PermissionMenuAction> permissionMenuActions = permissionMenuActionService.getBaseMapper().selectList(
                new LambdaQueryWrapper<PermissionMenuAction>().eq(PermissionMenuAction::getMenuId, menuId)
        );
        List<String> actionIds = permissionMenuActions.stream().map(PermissionMenuAction::getActionId).collect(Collectors.toList());
        return Result.ok(actionIds);
    }

    @ApiOperation(value = "获取用户权限", notes = "")
    @GetMapping("/query/user/{userId}")
    public Result<List<PermissionAction>> doQueryFromUser(@PathVariable("userId") String userId) {
        List<PermissionAction> permissionActions = this.permissionActionService.getFromUser(userId);
        return Result.ok(permissionActions);
    }


}
