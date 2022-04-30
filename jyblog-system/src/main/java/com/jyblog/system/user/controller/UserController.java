package com.jyblog.system.user.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyblog.domain.PageResult;
import com.jyblog.domain.Result;
import com.jyblog.log.annotation.Log;
import com.jyblog.system.user.domain.User;
import com.jyblog.system.user.model.vo.UserCreateVO;
import com.jyblog.system.user.model.vo.UserQueryVO;
import com.jyblog.system.user.model.vo.UserUpdatePasswordVO;
import com.jyblog.system.user.model.vo.UserUpdateVO;
import com.jyblog.system.user.service.UserService;
import com.jyblog.util.PageUtil;
import com.jyblog.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Objects;
import java.util.Set;

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

    @ApiOperation(value = "新增用户", notes = "")
    @PostMapping("/create")
    public Result<Object> doCreate(@RequestBody @Valid UserCreateVO vo) {
        vo.setPassword(new BCryptPasswordEncoder().encode(vo.getPassword()));
        return ResultUtil.toResult(userService.save(BeanUtil.copyProperties(vo, User.class)));
    }

    @ApiOperation(value = "更新用户", notes = "")
    @PutMapping("/update")
    public Result<Object> doUpdate(@RequestBody @Valid UserUpdateVO vo) {
        User user = userService.getById(vo.getId());
        BeanUtil.copyProperties(vo, user);
        return ResultUtil.toResult(userService.updateById(user));
    }

    @ApiOperation(value = "更新密码", notes = "")
    @PutMapping("/update/password")
    public Result<Object> doUpdatePassword(@RequestBody @Valid UserUpdatePasswordVO vo) {
        vo.setPassword(new BCryptPasswordEncoder().encode(vo.getPassword()));
        User user = userService.getById(vo.getId());
        BeanUtil.copyProperties(vo, user);
        return ResultUtil.toResult(userService.updateById(user));
    }

    @ApiOperation(value = "删除用户", notes = "")
    @DeleteMapping("/remove")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(userService.removeByIds(ids));
    }

    @ApiOperation(value = "根据ID获取当前用户信息", notes = "")
    @GetMapping("/query/{id}")
    public Result<Object> doQueryById(@PathVariable String id) {
        return Result.ok(userService.getById(id));
    }

    @Log(title = "系统管理：用户管理", desc = "分页查询用户")
    @ApiOperation(value = "分页查询用户", notes = "")
    @GetMapping("/query")
    public PageResult<User> doQueryPage(UserQueryVO vo) {
        int i = 1 / 0;
        return PageUtil.toPageResult(
                this.userService.page(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                        new LambdaQueryWrapper<User>()
                        .like(StringUtils.isNotBlank(vo.getUsername()), User::getUsername, vo.getUsername())
                        .like(StringUtils.isNotBlank(vo.getNickname()), User::getNickname, vo.getNickname())
                        .like(StringUtils.isNotBlank(vo.getPhone()), User::getPhone, vo.getPhone())
                        .eq(Objects.nonNull(vo.getType()), User::getType, vo.getType())
                        .eq(Objects.nonNull(vo.getStatus()), User::getStatus, vo.getStatus())
                )
        );
    }

}
