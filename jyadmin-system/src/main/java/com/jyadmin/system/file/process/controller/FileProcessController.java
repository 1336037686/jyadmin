package com.jyadmin.system.file.process.controller;

import cn.hutool.core.io.file.FileNameUtil;
import com.jyadmin.annotation.RateLimit;
import com.jyadmin.domain.Result;
import com.jyadmin.log.annotation.Log;
import com.jyadmin.system.file.manage.domain.FileRecord;
import com.jyadmin.system.file.manage.service.FileRecordService;
import com.jyadmin.system.file.process.domain.FileProcess;
import com.jyadmin.system.file.process.model.dto.FileProcessDownloadDTO;
import com.jyadmin.system.file.process.model.dto.FileProcessUploadDTO;
import com.jyadmin.system.file.process.service.FileProcessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-17 22:57 <br>
 * @description: FileProcessController <br>
 */
@Slf4j
@Api(value = "附件处理", tags = {"系统：附件处理接口"})
@RequestMapping("/api/file-process")
@RestController
public class FileProcessController {

    @Resource
    private FileProcessService fileProcessService;

    @Resource
    private FileRecordService fileRecordService;

    @RateLimit
    @Log(title = "附件处理：文件上传", desc = "文件上传")
    @ApiOperation(value = "文件上传", notes = "")
    @PostMapping("upload/{relevance}")
    public Result<FileProcess> doUpload(MultipartFile file, @PathVariable("relevance") String relevance) throws Exception {
        // 文件原名称
        String originalFilename = file.getOriginalFilename();
        // 文件后缀
        String suffix = FileNameUtil.getSuffix(originalFilename);
        // 新文件名
        String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + suffix;
        // 文件大小
        long fileSize = file.getSize();
        // 文件类型
        String contentType = file.getContentType();

        FileProcessUploadDTO fileProcessUploadDTO = new FileProcessUploadDTO()
                .setRealName(originalFilename).setName(newFileName)
                .setSuffix(suffix).setType(contentType).setSize(fileSize)
                .setRelevance(relevance).setInputStream(file.getInputStream());

        log.debug("upload file params: {}, {}", relevance, fileProcessUploadDTO);

        FileProcess result = fileProcessService.upload(fileProcessUploadDTO);

        return Result.ok(result);
    }

    @Log(title = "附件处理：文件下载", desc = "文件下载")
    @ApiOperation(value = "文件下载", notes = "")
    @GetMapping("download/{id}")
    public Result<Object> doDownload(@PathVariable("id") String id, HttpServletResponse response) {
        FileRecord fileRecord = fileRecordService.getById(Long.parseLong(id));
        Assert.notNull(fileRecord, "当前文件不存在！");
        FileProcessDownloadDTO fileProcessDownloadDTO = new FileProcessDownloadDTO().setFileRecord(fileRecord).setResponse(response);
        fileProcessService.download(fileProcessDownloadDTO);
        return Result.ok();
    }
}
