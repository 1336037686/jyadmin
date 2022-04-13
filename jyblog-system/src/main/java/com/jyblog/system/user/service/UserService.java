package com.jyblog.system.user.service;

import com.jyblog.system.user.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 13360
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2022-04-12 23:19:40
*/
public interface UserService extends IService<User> {

    User getByUserName(String userName);


}
