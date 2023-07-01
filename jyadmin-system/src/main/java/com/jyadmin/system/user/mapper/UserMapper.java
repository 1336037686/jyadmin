package com.jyadmin.system.user.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.system.user.domain.User;
import com.jyadmin.system.user.model.dto.UserDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 13360
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2022-04-12 23:19:40
* @Entity com.jyblog.system.user.domain.User
*/
public interface UserMapper extends BaseMapper<User> {

    List<String> selectRoleNamesByUserId(@Param("userId") Long userId);

    Page<UserDTO> selectUserPage(Page<User> page, @Param(Constants.WRAPPER) LambdaQueryWrapper<User> wrapper);

    UserDTO selectUserDetailById(@Param("userId") Long userId);
}




