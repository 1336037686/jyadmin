package com.jyadmin.system.email.record.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.annotation.RateLimit;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.log.annotation.Log;
import com.jyadmin.system.email.record.domain.EmailRecord;
import com.jyadmin.system.email.record.model.vo.EmailRecordQueryVO;
import com.jyadmin.system.email.record.service.EmailRecordService;
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
 * @description: EmailRecordController <br>
 */
@Slf4j
@Api(value = "系统邮件记录", tags = {"系统：系统邮件记录管理接口"})
@RequestMapping("/api/email-record")
@RestController
public class EmailRecordController {


    @Resource
    private EmailRecordService emailRecordService;

    @RateLimit
    @Log(title = "系统邮件记录：删除邮件记录", desc = "删除邮件记录")
    @ApiOperation(value = "删除邮件记录", notes = "")
    @DeleteMapping("/remove")
    @PreAuthorize("@jy.check('email-record:remove')")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(emailRecordService.removeByIds(DataUtil.convertToLongForSet(ids)));
    }

    @ApiOperation(value = "根据ID获取当前邮件记录信息", notes = "")
    @GetMapping("/query/{id}")
    @PreAuthorize("@jy.check('email-record:queryById')")
    public Result<Object> doQueryById(@PathVariable String id) {
        EmailRecord emailRecord = emailRecordService.getById(Long.parseLong(id));
        return Result.ok(emailRecord);
    }

    @ApiOperation(value = "列表查询邮件记录", notes = "")
    @GetMapping("/list")
    @PreAuthorize("@jy.check('email-record:list')")
    public Result<List<EmailRecord>> doQueryList(EmailRecordQueryVO vo) {
        List<EmailRecord> fileRecords = this.emailRecordService.list(new LambdaQueryWrapper<EmailRecord>()
                .like(StringUtils.isNotBlank(vo.getSender()), EmailRecord::getSender, vo.getSender())
                .like(StringUtils.isNotBlank(vo.getReceiver()), EmailRecord::getReceiver, vo.getReceiver())
                .like(StringUtils.isNotBlank(vo.getSubject()), EmailRecord::getSubject, vo.getSubject())
                .eq(StringUtils.isNotBlank(vo.getSource()), EmailRecord::getSource, vo.getSource())
                .eq(StringUtils.isNotBlank(vo.getRelevance()), EmailRecord::getRelevance, vo.getRelevance())
                .eq(StringUtils.isNotBlank(vo.getSource()), EmailRecord::getSource, vo.getSource())
                .orderByDesc(EmailRecord::getCreateTime)
        );
        return Result.ok(fileRecords);
    }

    @ApiOperation(value = "分页查询邮件记录", notes = "")
    @GetMapping("/query")
    @PreAuthorize("@jy.check('email-record:query')")
    public PageResult<EmailRecord> doQueryPage(EmailRecordQueryVO vo) {
        Page<EmailRecord> page = this.emailRecordService.page(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                new LambdaQueryWrapper<EmailRecord>()
                        .like(StringUtils.isNotBlank(vo.getSender()), EmailRecord::getSender, vo.getSender())
                        .like(StringUtils.isNotBlank(vo.getReceiver()), EmailRecord::getReceiver, vo.getReceiver())
                        .like(StringUtils.isNotBlank(vo.getSubject()), EmailRecord::getSubject, vo.getSubject())
                        .eq(StringUtils.isNotBlank(vo.getSource()), EmailRecord::getSource, vo.getSource())
                        .eq(StringUtils.isNotBlank(vo.getRelevance()), EmailRecord::getRelevance, vo.getRelevance())
                        .eq(StringUtils.isNotBlank(vo.getSource()), EmailRecord::getSource, vo.getSource())
                        .orderByDesc(EmailRecord::getCreateTime)
        );
        return PageUtil.toPageResult(page);
    }

}
