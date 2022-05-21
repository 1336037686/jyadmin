package com.jyadmin.module.tag.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyadmin.module.tag.domain.Tag;
import com.jyadmin.module.tag.mapper.TagMapper;
import com.jyadmin.module.tag.service.TagService;
import org.springframework.stereotype.Service;

/**
* @author 13360
* @description 针对表【tb_tag(博客标签表)】的数据库操作Service实现
* @createDate 2022-04-09 03:35:58
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService{

}




