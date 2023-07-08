package com.jyadmin.system.permission.group.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.annotation.RateLimit;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.log.annotation.Log;
import com.jyadmin.system.permission.group.domain.PermissionActionGroup;
import com.jyadmin.system.permission.group.model.vo.PermissionGroupCreateVO;
import com.jyadmin.system.permission.group.model.vo.PermissionGroupQueryVO;
import com.jyadmin.system.permission.group.model.vo.PermissionGroupUpdateVO;
import com.jyadmin.system.permission.group.service.PermissionActionGroupService;
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
 * @author LGX_TvT
 * @date 2022-04-14 15:57
 */
@Slf4j
@Api(value = "系统权限组别", tags = {"系统：系统权限组别接口"})
@RequestMapping("/api/permission/group")
@RestController
public class PermissionGroupController {

    @Resource
    private PermissionActionGroupService permissionActionGroupService;

    @RateLimit
    @Log(title = "系统管理：新增组别", desc = "新增组别")
    @ApiOperation(value = "新增组别", notes = "")
    @PostMapping("/create")
    @PreAuthorize("@jy.check('group:create')")
    public Result<Object> doCreate(@RequestBody @Valid PermissionGroupCreateVO vo) {
        return ResultUtil.toResult(permissionActionGroupService.save(BeanUtil.copyProperties(vo, PermissionActionGroup.class)));
    }

    @RateLimit
    @Log(title = "系统管理：更新组别", desc = "更新组别")
    @ApiOperation(value = "更新组别", notes = "")
    @PutMapping("/update")
    @PreAuthorize("@jy.check('group:update')")
    public Result<Object> doUpdate(@RequestBody @Valid PermissionGroupUpdateVO vo) {
        PermissionActionGroup permissionActionGroup = permissionActionGroupService.getById(vo.getId());
        BeanUtil.copyProperties(vo, permissionActionGroup);
        return ResultUtil.toResult(permissionActionGroupService.updateById(permissionActionGroup));
    }

    @RateLimit
    @Log(title = "系统管理：删除组别", desc = "删除组别")
    @ApiOperation(value = "删除组别", notes = "")
    @DeleteMapping("/remove")
    @PreAuthorize("@jy.check('group:remove')")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(permissionActionGroupService.removeByIds(DataUtil.convertToLongForSet(ids)));
    }

    @ApiOperation(value = "根据ID查找组别信息", notes = "")
    @GetMapping("/query/{id}")
    @PreAuthorize("@jy.check('group:queryById')")
    public Result<Object> doQueryById(@PathVariable String id) {
        return Result.ok(permissionActionGroupService.getById(Long.parseLong(id)));
    }

    @ApiOperation(value = "列表查询组别信息", notes = "")
    @GetMapping("/list")
    @PreAuthorize("@jy.check('group:list')")
    public Result<List<PermissionActionGroup>> doQueryList(PermissionGroupQueryVO vo) {
        return Result.ok(this.permissionActionGroupService.getBaseMapper().selectList(
                new LambdaQueryWrapper<PermissionActionGroup>()
                .eq(Objects.nonNull(vo.getStatus()), PermissionActionGroup::getStatus, vo.getStatus())
                .orderByAsc(PermissionActionGroup::getSort)
        ));
    }

    @ApiOperation(value = "分页查询组别信息", notes = "")
    @GetMapping("/query")
    @PreAuthorize("@jy.check('group:query')")
    public PageResult<PermissionActionGroup> doQueryPage(PermissionGroupQueryVO vo) {
        return PageUtil.toPageResult(
                this.permissionActionGroupService.page(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                        new LambdaQueryWrapper<PermissionActionGroup>()
                                .like(StringUtils.isNotBlank(vo.getName()), PermissionActionGroup::getName, vo.getName())
                                .like(StringUtils.isNotBlank(vo.getCode()), PermissionActionGroup::getCode, vo.getCode())
                                .eq(Objects.nonNull(vo.getParentId()), PermissionActionGroup::getParentId, vo.getParentId())
                                .eq(Objects.nonNull(vo.getStatus()), PermissionActionGroup::getStatus, vo.getStatus())
                                .orderByAsc(PermissionActionGroup::getSort)
                )
        );
    }
}
