package com.jyadmin.module.tag.mapper;

import com.jyadmin.module.tag.domain.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 13360
* @description 针对表【tb_tag(博客标签表)】的数据库操作Mapper
* @createDate 2022-04-09 03:35:58
* @Entity com.jyblog.module.tag.domain.Tag
*/
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}




