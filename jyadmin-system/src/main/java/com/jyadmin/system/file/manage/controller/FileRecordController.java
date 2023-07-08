package com.jyadmin.system.file.manage.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.annotation.RateLimit;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.log.annotation.Log;
import com.jyadmin.system.file.manage.domain.FileRecord;
import com.jyadmin.system.file.manage.model.vo.FileRecordQueryVO;
import com.jyadmin.system.file.manage.service.FileRecordService;
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
 * 附件记录
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-17 22:49 <br>
 * @description: FileRecordController <br>
 */
@Slf4j
@Api(value = "附件记录", tags = {"系统：附件记录管理接口"})
@RequestMapping("/api/file-record")
@RestController
public class FileRecordController {

    @Resource
    private FileRecordService fileRecordService;

    @RateLimit
    @Log(title = "系统附件记录：删除附件记录", desc = "删除附件记录")
    @ApiOperation(value = "删除附件记录", notes = "")
    @DeleteMapping("/remove")
    @PreAuthorize("@jy.check('file-record:remove')")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(fileRecordService.removeByIds(DataUtil.convertToLongForSet(ids)));
    }

    @ApiOperation(value = "根据ID获取当前附件记录信息", notes = "")
    @GetMapping("/query/{id}")
    @PreAuthorize("@jy.check('file-record:queryById')")
    public Result<Object> doQueryById(@PathVariable String id) {
        FileRecord fileRecord = fileRecordService.getById(Long.parseLong(id));
        return Result.ok(fileRecord);
    }

    @ApiOperation(value = "列表查询附件记录", notes = "")
    @GetMapping("/list")
    @PreAuthorize("@jy.check('file-record:list')")
    public Result<List<FileRecord>> doQueryList(FileRecordQueryVO vo) {
        List<FileRecord> fileRecords = this.fileRecordService.list(new LambdaQueryWrapper<FileRecord>()
                .like(StringUtils.isNotBlank(vo.getName()), FileRecord::getName, vo.getName())
                .eq(StringUtils.isNotBlank(vo.getType()), FileRecord::getType, vo.getType())
                .eq(StringUtils.isNotBlank(vo.getSuffix()), FileRecord::getSuffix, vo.getSuffix())
                .eq(StringUtils.isNotBlank(vo.getSource()), FileRecord::getSource, vo.getSource())
                .like(StringUtils.isNotBlank(vo.getRealName()), FileRecord::getRealName, vo.getRealName())
                .eq(StringUtils.isNotBlank(vo.getRelevance()), FileRecord::getRelevance, vo.getRelevance())
                .orderByDesc(FileRecord::getCreateTime)
        );
        return Result.ok(fileRecords);
    }

    @ApiOperation(value = "分页查询附件记录", notes = "")
    @GetMapping("/query")
    @PreAuthorize("@jy.check('file-record:query')")
    public PageResult<FileRecord> doQueryPage(FileRecordQueryVO vo) {
        Page<FileRecord> page = this.fileRecordService.page(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                new LambdaQueryWrapper<FileRecord>()
                        .like(StringUtils.isNotBlank(vo.getName()), FileRecord::getName, vo.getName())
                        .eq(StringUtils.isNotBlank(vo.getType()), FileRecord::getType, vo.getType())
                        .eq(StringUtils.isNotBlank(vo.getSuffix()), FileRecord::getSuffix, vo.getSuffix())
                        .eq(StringUtils.isNotBlank(vo.getSource()), FileRecord::getSource, vo.getSource())
                        .like(StringUtils.isNotBlank(vo.getRealName()), FileRecord::getRealName, vo.getRealName())
                        .eq(StringUtils.isNotBlank(vo.getRelevance()), FileRecord::getRelevance, vo.getRelevance())
                        .orderByDesc(FileRecord::getCreateTime)
        );
        return PageUtil.toPageResult(page);
    }

}
