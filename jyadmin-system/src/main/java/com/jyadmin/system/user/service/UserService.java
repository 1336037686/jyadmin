package com.jyadmin.system.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jyadmin.system.user.domain.User;
import com.jyadmin.system.user.model.dto.UserDTO;

/**
* @author 13360
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2022-04-12 23:19:40
*/
public interface UserService extends IService<User> {

    User getByUserName(String userName);

    Page<UserDTO> getPage(Page<User> result, LambdaQueryWrapper<User> wrapper);

    UserDTO getUserDetailById(Long id);
}
