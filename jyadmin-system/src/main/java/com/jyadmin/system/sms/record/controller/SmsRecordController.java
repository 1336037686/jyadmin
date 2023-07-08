package com.jyadmin.system.sms.record.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.annotation.RateLimit;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.log.annotation.Log;
import com.jyadmin.system.sms.record.domain.SmsRecord;
import com.jyadmin.system.sms.record.model.vo.SmsRecordQueryVO;
import com.jyadmin.system.sms.record.service.SmsRecordService;
import com.jyadmin.util.DataUtil;
import com.jyadmin.util.PageUtil;
import com.jyadmin.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-23 23:08 <br>
 * @description: SmsRecordController <br>
 */
@Slf4j
@Api(value = "系统短信记录", tags = {"系统：系统短信记录管理接口"})
@RequestMapping("/api/sms-record")
@RestController
public class SmsRecordController {

    @Resource
    private SmsRecordService smsRecordService;

    @RateLimit
    @Log(title = "系统短信记录：删除短信记录", desc = "删除短信记录")
    @ApiOperation(value = "删除短信记录", notes = "")
    @DeleteMapping("/remove")
    @PreAuthorize("@jy.check('sms-record:remove')")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(smsRecordService.removeByIds(DataUtil.convertToLongForSet(ids)));
    }

    @ApiOperation(value = "根据ID获取当前短信记录信息", notes = "")
    @GetMapping("/query/{id}")
    @PreAuthorize("@jy.check('sms-record:queryById')")
    public Result<Object> doQueryById(@PathVariable("id") String id) {
        SmsRecord smsRecord = smsRecordService.getById(Long.parseLong(id));
        return Result.ok(smsRecord);
    }

    @ApiOperation(value = "列表查询短信记录", notes = "")
    @GetMapping("/list")
    @PreAuthorize("@jy.check('sms-record:list')")
    public Result<List<SmsRecord>> doQueryList(SmsRecordQueryVO vo) {
        List<SmsRecord> fileRecords = this.smsRecordService.list(new LambdaQueryWrapper<SmsRecord>()
                .like(StringUtils.isNotBlank(vo.getPhone()), SmsRecord::getPhone, vo.getPhone())
                .eq(StringUtils.isNotBlank(vo.getSource()), SmsRecord::getSource, vo.getSource())
                .eq(StringUtils.isNotBlank(vo.getRelevance()), SmsRecord::getRelevance, vo.getRelevance())
                .orderByDesc(SmsRecord::getCreateTime)
        );
        return Result.ok(fileRecords);
    }

    @ApiOperation(value = "分页查询短信记录", notes = "")
    @GetMapping("/query")
    @PreAuthorize("@jy.check('sms-record:query')")
    public PageResult<SmsRecord> doQueryPage(SmsRecordQueryVO vo) {
        Page<SmsRecord> page = this.smsRecordService.page(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                new LambdaQueryWrapper<SmsRecord>()
                        .like(StringUtils.isNotBlank(vo.getPhone()), SmsRecord::getPhone, vo.getPhone())
                        .eq(StringUtils.isNotBlank(vo.getSource()), SmsRecord::getSource, vo.getSource())
                        .eq(StringUtils.isNotBlank(vo.getRelevance()), SmsRecord::getRelevance, vo.getRelevance())
                        .orderByDesc(SmsRecord::getCreateTime)
        );
        return PageUtil.toPageResult(page);
    }

}
