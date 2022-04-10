package com.jyblog.module.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyblog.module.blog.domain.Blog;
import com.jyblog.module.blog.service.BlogService;
import com.jyblog.module.blog.mapper.BlogMapper;
import org.springframework.stereotype.Service;

/**
* @author 13360
* @description 针对表【tb_blog(博客文章表)】的数据库操作Service实现
* @createDate 2022-04-09 16:39:13
*/
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog>
    implements BlogService{

}




