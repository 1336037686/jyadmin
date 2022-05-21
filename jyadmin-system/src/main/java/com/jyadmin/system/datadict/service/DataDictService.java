package com.jyadmin.system.datadict.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.system.datadict.domain.DataDict;
import com.jyadmin.system.datadict.model.dto.DataDictQueryDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
* @author 13360
* @description 针对表【sys_data_dict(数据字典表)】的数据库操作Service
* @createDate 2022-04-07 23:49:26
*/
public interface DataDictService extends IService<DataDict> {

    boolean removeByIds(Collection<?> list);

    List<DataDict> getChildById(String id);

    Page<DataDict> getPage(DataDictQueryDTO copyProperties);
}
