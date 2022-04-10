package com.jyblog.system.datadict.service;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.jyblog.system.datadict.domain.SysDataDict;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jyblog.system.datadict.model.dto.SysDataDictQueryDTO;

import java.util.Collection;
import java.util.List;

/**
* @author 13360
* @description 针对表【sys_data_dict(数据字典表)】的数据库操作Service
* @createDate 2022-04-07 23:49:26
*/
public interface SysDataDictService extends IService<SysDataDict> {

    boolean removeByIds(Collection<?> list);

    List<SysDataDict> getChildById(String id);

    Page<SysDataDict> getPage(SysDataDictQueryDTO copyProperties);
}
