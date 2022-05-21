package com.jyadmin.module.category.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyadmin.module.category.domain.Category;
import com.jyadmin.module.category.mapper.CategoryMapper;
import com.jyadmin.module.category.service.CategoryService;
import org.springframework.stereotype.Service;

/**
* @author 13360
* @description 针对表【tb_category(博客类别表)】的数据库操作Service实现
* @createDate 2022-04-09 03:40:34
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}




