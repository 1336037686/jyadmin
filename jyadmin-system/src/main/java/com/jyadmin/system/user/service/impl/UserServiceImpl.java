package com.jyadmin.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyadmin.system.user.model.dto.UserDTO;
import com.jyadmin.system.user.service.UserService;
import com.jyadmin.system.user.domain.User;
import com.jyadmin.system.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 13360
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2022-04-12 23:19:40
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User getByUserName(String userName) {
        return this.baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, userName));
    }

    @Override
    public Page<UserDTO> getPage(Page<User> page, LambdaQueryWrapper<User> wrapper) {
        return this.baseMapper.selectUserPage(page, wrapper);
    }

    @Override
    public UserDTO getUserDetailById(Long id) {
        return this.baseMapper.selectUserDetailById(id);
    }
}




