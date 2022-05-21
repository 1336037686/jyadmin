package com.jyadmin.module.category.mapper;

import com.jyadmin.module.category.domain.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 13360
* @description 针对表【tb_category(博客类别表)】的数据库操作Mapper
* @createDate 2022-04-09 03:40:34
* @Entity com.jyblog.module.category.domain.Category
*/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}




