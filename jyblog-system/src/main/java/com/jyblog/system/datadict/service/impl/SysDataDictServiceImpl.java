package com.jyblog.system.datadict.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyblog.system.datadict.domain.SysDataDict;
import com.jyblog.system.datadict.model.dto.SysDataDictQueryDTO;
import com.jyblog.system.datadict.service.SysDataDictService;
import com.jyblog.system.datadict.mapper.SysDataDictMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
* @author 13360
* @description 针对表【sys_data_dict(数据字典表)】的数据库操作Service实现
* @createDate 2022-04-07 23:49:26
*/
@Service
public class SysDataDictServiceImpl extends ServiceImpl<SysDataDictMapper, SysDataDict> implements SysDataDictService{

    public boolean removeByIds(Collection<?> list) {
        // 层序便利，获取当前节点以及下属节点
        Set<String> ids = list.stream().map(Object::toString).collect(Collectors.toSet());
        LinkedList<SysDataDict> queue = new LinkedList<>();
        List<SysDataDict> baseSysDataDicts = this.baseMapper.selectList(new LambdaQueryWrapper<SysDataDict>().in(SysDataDict::getId, list));
        queue.addAll(baseSysDataDicts);
        while (!queue.isEmpty()) {
            int size = queue.size();
            Set<String> inIds = new HashSet<>();
            for (int i = 0; i < size; i++) inIds.add(Objects.requireNonNull(queue.pollFirst()).getId());
            List<SysDataDict> sysDataDicts = this.baseMapper.selectList(new LambdaQueryWrapper<SysDataDict>().in(SysDataDict::getParentId, inIds));
            if (CollUtil.isEmpty(sysDataDicts)) continue;
            queue.addAll(sysDataDicts);
            ids.addAll(sysDataDicts.stream().map(SysDataDict::getId).collect(Collectors.toList()));
        }
        return super.removeByIds(ids);
    }

    @Override
    public List<SysDataDict> getChildById(String id) {
        return this.baseMapper.selectList(new LambdaQueryWrapper<SysDataDict>().eq(SysDataDict::getParentId, id));
    }

    @Override
    public Page<SysDataDict> getPage(SysDataDictQueryDTO dto) {
        return this.baseMapper.selectPage(new Page<>(dto.getPageNumber(), dto.getPageSize()),
                new LambdaQueryWrapper<SysDataDict>().eq(SysDataDict::getIsRoot, 1)
                        .like(StringUtils.isNotBlank(dto.getDictType()), SysDataDict::getDictType, dto.getDictType())
                        .like(StringUtils.isNotBlank(dto.getName()), SysDataDict::getName, dto.getName())
                        .like(StringUtils.isNotBlank(dto.getCode()), SysDataDict::getCode, dto.getCode())
                );
    }

}




