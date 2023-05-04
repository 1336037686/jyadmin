package com.jyadmin.system.post.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyadmin.system.post.domain.Post;
import com.jyadmin.system.post.service.PostService;
import com.jyadmin.system.post.mapper.PostMapper;
import org.springframework.stereotype.Service;

/**
* @author 13360
* @description 针对表【sys_post(岗位表)】的数据库操作Service实现
* @createDate 2023-05-04 22:47:28
*/
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post>
    implements PostService{

}




