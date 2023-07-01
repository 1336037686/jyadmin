package com.jyadmin.system.datadict.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jyadmin.system.datadict.domain.DataDict;
import com.jyadmin.system.datadict.model.dto.DataDictQueryDTO;
import com.jyadmin.system.datadict.model.vo.DataDictQueryVO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
* @author 13360
* @description 针对表【sys_data_dict(数据字典表)】的数据库操作Service
* @createDate 2022-04-07 23:49:26
*/
public interface DataDictService extends IService<DataDict> {

    boolean removeByIds(Set<Long> list);

    List<DataDict> getChildById(Long id);

    Page<DataDict> getPage(DataDictQueryDTO copyProperties);

    List<Map<String, Object>> getLayer(DataDictQueryVO vo);
}
