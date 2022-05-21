package com.jyadmin.system.permission.group.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyadmin.system.permission.action.domain.PermissionAction;
import com.jyadmin.system.permission.action.mapper.PermissionActionMapper;
import com.jyadmin.system.permission.group.domain.PermissionActionGroup;
import com.jyadmin.system.permission.group.mapper.PermissionActionGroupMapper;
import com.jyadmin.system.permission.group.service.PermissionActionGroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 13360
* @description 针对表【sys_permission_action_group(系统权限动作组别表)】的数据库操作Service实现
* @createDate 2022-04-13 23:21:16
*/
@Service
public class PermissionActionGroupServiceImpl extends ServiceImpl<PermissionActionGroupMapper, PermissionActionGroup> implements PermissionActionGroupService {

    @Resource
    private PermissionActionMapper permissionActionMapper;

    @Transactional
    @Override
    public boolean removeByIds(Collection<?> list) {
        // 删除当前组别 及其 下属接口
        List<PermissionAction> permissionActions = permissionActionMapper.selectList(new LambdaQueryWrapper<PermissionAction>().in(PermissionAction::getGroupId, list));
        if (CollectionUtil.isNotEmpty(permissionActions)) {
            permissionActionMapper.deleteBatchIds(permissionActions.stream().map(PermissionAction::getId).collect(Collectors.toList()));
        }
        super.removeByIds(list);
        return true;
    }
}




