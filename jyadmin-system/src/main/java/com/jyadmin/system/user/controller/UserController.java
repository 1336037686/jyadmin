package com.jyadmin.system.user.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.annotation.RateLimit;
import com.jyadmin.consts.GlobalConstants;
import com.jyadmin.consts.ResultStatus;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.exception.ApiException;
import com.jyadmin.log.annotation.Log;
import com.jyadmin.system.user.domain.User;
import com.jyadmin.system.user.model.dto.UserDTO;
import com.jyadmin.system.user.model.vo.*;
import com.jyadmin.system.user.service.UserService;
import com.jyadmin.util.PageUtil;
import com.jyadmin.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统用户
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-12 23:20 <br>
 * @description: UserController <br>
 */
@Slf4j
@Api(value = "系统用户", tags = "系统：系统用户接口")
@RequestMapping("/api/user")
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @RateLimit
    @Log(title = "系统管理：新增用户", desc = "新增用户")
    @ApiOperation(value = "新增用户", notes = "")
    @PostMapping("/create")
    @PreAuthorize("@jy.check('user:create')")
    public Result<Object> doCreate(@RequestBody @Valid UserCreateVO vo) {
        vo.setPassword(new BCryptPasswordEncoder().encode(vo.getPassword()));
        return ResultUtil.toResult(userService.save(BeanUtil.copyProperties(vo, User.class)));
    }

    @RateLimit
    @Log(title = "系统管理：更新用户", desc = "更新用户")
    @ApiOperation(value = "更新用户", notes = "")
    @PutMapping("/update")
    @PreAuthorize("@jy.check('user:update')")
    public Result<Object> doUpdate(@RequestBody @Valid UserUpdateVO vo) {
        User user = userService.getById(vo.getId());
        BeanUtil.copyProperties(vo, user);
        return ResultUtil.toResult(userService.updateById(user));
    }

    @RateLimit
    @Log(title = "系统管理：更新密码", desc = "更新密码")
    @ApiOperation(value = "更新密码", notes = "")
    @PutMapping("/update/password")
    @PreAuthorize("@jy.check('user:updatepwd')")
    public Result<Object> doUpdatePassword(@RequestBody @Valid UserUpdatePasswordVO vo) {
        vo.setPassword(new BCryptPasswordEncoder().encode(vo.getPassword()));
        User user = userService.getById(vo.getId());
        BeanUtil.copyProperties(vo, user);
        return ResultUtil.toResult(userService.updateById(user));
    }

    @RateLimit
    @Log(title = "系统管理：更新用户自身密码", desc = "更新用户自身密码")
    @ApiOperation(value = "更新用户自身密码", notes = "")
    @PutMapping("/update/user-password")
    public Result<Object> doUpdateUserPassword(@RequestBody @Valid UserUpdateOwnPasswordVO vo) {
        User user = userService.getById(vo.getId());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(vo.getOldPassword(), user.getPassword())) {
            throw new ApiException(ResultStatus.FAIL, "输入的旧密码错误");
        }
        user.setPassword(new BCryptPasswordEncoder().encode(vo.getNewPassword()));
        return ResultUtil.toResult(userService.updateById(user));
    }

    @RateLimit
    @Log(title = "系统管理：删除用户", desc = "删除用户")
    @ApiOperation(value = "删除用户", notes = "")
    @DeleteMapping("/remove")
    @PreAuthorize("@jy.check('user:remove')")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)) return Result.fail();
        Set<Long> newIds = ids.stream().map(Long::parseLong).collect(Collectors.toSet());
        return ResultUtil.toResult(userService.removeByIds(newIds));
    }

    @ApiOperation(value = "根据ID获取当前用户信息", notes = "")
    @GetMapping("/query/{id}")
    @PreAuthorize("@jy.check('user:queryById')")
    public Result<Object> doQueryById(@PathVariable String id) {
        UserDTO user = userService.getUserDetailById(Long.parseLong(id));
        return Result.ok(user);
    }

    @ApiOperation(value = "分页查询用户", notes = "")
    @GetMapping("/query")
    @PreAuthorize("@jy.check('user:query')")
    public PageResult<UserDTO> doQueryPage(UserQueryVO vo) {
        return PageUtil.toPageResult(this.userService.getPage(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                new LambdaQueryWrapper<User>()
                        .like(StringUtils.isNotBlank(vo.getUsername()), User::getUsername, vo.getUsername())
                        .like(StringUtils.isNotBlank(vo.getNickname()), User::getNickname, vo.getNickname())
                        .like(StringUtils.isNotBlank(vo.getPhone()), User::getPhone, vo.getPhone())
                        .eq(Objects.nonNull(vo.getType()), User::getType, vo.getType())
                        .eq(Objects.nonNull(vo.getStatus()), User::getStatus, vo.getStatus())
                        .eq(Objects.nonNull(vo.getDepartment()), User::getDepartment, vo.getDepartment())
                        .eq(Objects.nonNull(vo.getPost()), User::getPost, vo.getPost())
                        .eq(true, User::getDeleted, GlobalConstants.SysDeleted.EXIST.getValue())
                        .orderByDesc(User::getCreateTime)
        ));
    }

}
