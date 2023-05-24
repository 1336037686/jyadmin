package com.jyadmin.monitor.offline.controller;

import com.jyadmin.annotation.RateLimit;
import com.jyadmin.consts.ResultStatus;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.domain.UserCacheInfo;
import com.jyadmin.exception.ApiException;
import com.jyadmin.log.annotation.Log;
import com.jyadmin.monitor.offline.model.vo.UserQueryVO;
import com.jyadmin.monitor.offline.service.OfflineService;
import com.jyadmin.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("@jy.check('offline:query')")
    public PageResult<UserCacheInfo> doQueryPage(UserQueryVO vo) {
        return PageUtil.toPageResult(offlineService.getList(vo), vo);
    }

    @RateLimit
    @Log(title = "用户下线：强制下线", desc = "强制下线")
    @ApiOperation(value = "强制下线", notes = "")
    @DeleteMapping("/forcedOffline")
    @PreAuthorize("@jy.check('offline:forcedOffline')")
    public Result<Object> doForcedOffline(@RequestBody String username) {
        // 强制下线时用户名不能为空
        if (StringUtils.isEmpty(username)) throw new ApiException(ResultStatus.PARAM_ERROR);
        offlineService.forcedOffline(username);
        return Result.ok();
    }
}
