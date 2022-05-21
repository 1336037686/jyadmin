package com.jyadmin.monitor.offline.controller;

import com.jyadmin.consts.JyResultStatus;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.domain.UserCacheInfo;
import com.jyadmin.exception.JyBusinessException;
import com.jyadmin.monitor.offline.model.vo.UserQueryVO;
import com.jyadmin.monitor.offline.service.OfflineService;
import com.jyadmin.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-05-13 22:39 <br>
 * @description: OfflineController <br>
 */
@Slf4j
@Api(value = "用户下线", tags = {"监控：用户下线接口"})
@RequestMapping("/api/offline")
@RestController
public class OfflineController {

    @Resource
    private OfflineService offlineService;

    @ApiOperation(value = "分页查询登录用户信息", notes = "")
    @GetMapping("/query")
    public PageResult<UserCacheInfo> doQueryPage(UserQueryVO vo) {
        return PageUtil.toPageResult(offlineService.getList(vo), vo);
    }

    @ApiOperation(value = "强制下线", notes = "")
    @DeleteMapping("/forcedOffline")
    public Result<Object> doForcedOffline(@RequestBody String username) {
        if (StringUtils.isEmpty(username)) {
            throw new JyBusinessException(JyResultStatus.FAIL, "强制下线时用户名不能为空");
        }
        offlineService.forcedOffline(username);
        return Result.ok();
    }
}
