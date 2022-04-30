package com.jyblog.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyblog.security.domain.PermissionAction;
import com.jyblog.security.domain.User;
import com.jyblog.security.mapper.AuthMapper;
import com.jyblog.security.service.AuthService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 13360
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2022-04-12 23:19:40
*/
@Service
public class AuthServiceImpl extends ServiceImpl<AuthMapper, User> implements AuthService {

    @Resource
    private AuthMapper authMapper;

    @Override
    public User getByUserName(String userName) {
        return this.baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, userName));
    }

    @Override
    public List<PermissionAction> getPermissions(String id) {
        return authMapper.selectPermissions(id);
    }
}




