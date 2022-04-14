package com.jyblog.system.permission.action.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyblog.domain.PageResult;
import com.jyblog.domain.Result;
import com.jyblog.system.permission.action.domain.PermissionAction;
import com.jyblog.system.permission.action.model.vo.PermissionActionCreateVO;
import com.jyblog.system.permission.action.model.vo.PermissionActionQueryVO;
import com.jyblog.system.permission.action.model.vo.PermissionActionUpdateVO;
import com.jyblog.system.permission.action.service.PermissionActionService;
import com.jyblog.util.PageUtil;
import com.jyblog.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Objects;
import java.util.Set;

/**
 * @author LGX_TvT
 * @date 2022-04-14 15:57
 */
@Slf4j
@Api(value = "系统权限动作", tags = {"系统权限动作接口"})
@RequestMapping("permission/action")
@RestController
public class PermissionActionController {

    @Resource
    private PermissionActionService permissionActionService;

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

}
