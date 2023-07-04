package com.jyadmin.system.datadict.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyadmin.system.datadict.domain.DataDict;
import com.jyadmin.system.datadict.mapper.DataDictMapper;
import com.jyadmin.system.datadict.model.dto.DataDictQueryDTO;
import com.jyadmin.system.datadict.model.vo.DataDictQueryVO;
import com.jyadmin.system.datadict.service.DataDictService;
import com.jyadmin.util.DataUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author 13360
* @description 针对表【sys_data_dict(数据字典表)】的数据库操作Service实现
* @createDate 2022-04-07 23:49:26
*/
@Service
public class DataDictServiceImpl extends ServiceImpl<DataDictMapper, DataDict> implements DataDictService {

    public boolean removeByIds(Set<Long> ids) {
        // 层序便利，获取当前节点以及下属节点
        LinkedList<DataDict> queue = new LinkedList<>();
        List<DataDict> baseSysDataDicts = this.baseMapper.selectList(new LambdaQueryWrapper<DataDict>().in(DataDict::getId, ids));
        queue.addAll(baseSysDataDicts);
        while (!queue.isEmpty()) {
            int size = queue.size();
            Set<Long> inIds = new HashSet<>();
            for (int i = 0; i < size; i++) inIds.add(Objects.requireNonNull(queue.pollFirst()).getId());
            List<DataDict> sysDataDicts = this.baseMapper.selectList(new LambdaQueryWrapper<DataDict>().in(DataDict::getParentId, inIds));
            if (CollUtil.isEmpty(sysDataDicts)) continue;
            queue.addAll(sysDataDicts);
            ids.addAll(sysDataDicts.stream().map(DataDict::getId).collect(Collectors.toList()));
        }
        return super.removeByIds(ids);
    }

    @Override
    public List<DataDict> getChildById(Long id) {
        return this.baseMapper.selectList(new LambdaQueryWrapper<DataDict>().eq(DataDict::getParentId, id));
    }

    @Override
    public Page<DataDict> getPage(DataDictQueryDTO dto) {
        return this.baseMapper.selectPage(new Page<>(dto.getPageNumber(), dto.getPageSize()),
                new LambdaQueryWrapper<DataDict>().eq(DataDict::getIsRoot, 1)
                        .like(StringUtils.isNotBlank(dto.getDictType()), DataDict::getDictType, dto.getDictType())
                        .like(StringUtils.isNotBlank(dto.getName()), DataDict::getName, dto.getName())
                        .like(StringUtils.isNotBlank(dto.getCode()), DataDict::getCode, dto.getCode())
                        .orderByAsc(DataDict::getSort)
                );
    }

    @Override
    public List<Map<String, Object>> getLayer(DataDictQueryVO vo) {
        List<DataDict> dataDictList = this.getBaseMapper().selectList(
                new LambdaQueryWrapper<DataDict>()
                        .like(StringUtils.isNotBlank(vo.getName()), DataDict::getName, vo.getName())
                        .like(StringUtils.isNotBlank(vo.getCode()), DataDict::getCode, vo.getCode())
                        .eq(Objects.nonNull(vo.getDictType()), DataDict::getParentId, vo.getDictType())
                        .orderByAsc(DataDict::getSort)
        );

        List<Map<String, Object>> dataDictMaps = dataDictList.stream().map(BeanUtil::beanToMap).peek(x -> {
            x.put("id", DataUtil.getValueForMap(x, "id"));
            x.put("parentId", DataUtil.getValueForMap(x, "parentId"));
            if (Objects.nonNull(x.get("createTime"))) x.put("createTime", ((LocalDateTime) x.get("createTime")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            DataUtil.removeItemForMap(x, "createBy", "updateBy", "updateTime", "deleted");
        }).collect(Collectors.toList());

        Set<String> childrenMenus = new HashSet<>();
        Map<String, Map<String, Object>> table = new HashMap<>();
        for (Map<String, Object> menu : dataDictMaps) table.put(menu.get("id").toString(), menu);
        for (Map<String, Object> menu : dataDictMaps) {
            Map<String, Object> parentMenu = table.get(menu.get("parentId").toString());
            if (Objects.isNull(parentMenu)) continue;
            List<Map<String, Object>> children = (List<Map<String, Object>>) parentMenu.getOrDefault("children", new ArrayList<>());
            children.add(menu);
            parentMenu.put("children", children);
            childrenMenus.add(menu.get("id").toString());
        }

        // 获取所有顶级节点
        return dataDictMaps.stream().filter(x -> !childrenMenus.contains(x.get("id").toString())).collect(Collectors.toList());
    }

}




