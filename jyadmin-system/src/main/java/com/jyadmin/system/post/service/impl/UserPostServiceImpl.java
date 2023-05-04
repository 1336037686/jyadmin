package com.jyadmin.system.post.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyadmin.system.post.domain.UserPost;
import com.jyadmin.system.post.service.UserPostService;
import com.jyadmin.system.post.mapper.UserPostMapper;
import org.springframework.stereotype.Service;

/**
* @author 13360
* @description 针对表【tr_user_post(系统用户角色中间表)】的数据库操作Service实现
* @createDate 2023-05-04 22:47:48
*/
@Service
public class UserPostServiceImpl extends ServiceImpl<UserPostMapper, UserPost>
    implements UserPostService{

}




