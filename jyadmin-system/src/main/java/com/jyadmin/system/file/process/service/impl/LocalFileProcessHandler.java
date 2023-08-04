package com.jyadmin.system.file.process.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import com.jyadmin.consts.ResultStatus;
import com.jyadmin.exception.ApiException;
import com.jyadmin.system.config.detail.domain.ConfigDetail;
import com.jyadmin.system.config.detail.service.ConfigDetailService;
import com.jyadmin.system.config.module.domain.ModuleConfigWrapper;
import com.jyadmin.system.file.manage.domain.FileRecord;
import com.jyadmin.system.file.manage.service.FileRecordService;
import com.jyadmin.system.file.process.domain.FileProcess;
import com.jyadmin.system.file.process.model.dto.FileProcessUploadDTO;
import com.jyadmin.system.file.process.service.FileProcessHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * 本地文件上传处理
 * 使用本地进行文件上传时候需要使用Nginx进行文件路径映射，否则会找不到文件
 * 映射路径应与配置的domain路径一致
 *
 * nginx 文件映射配置
 *     server {
 *         listen       8082;
 *         server_name  localhost;
 *
 *         location /file {   # 文件访问路径 ---> server_name:listen/file = Domain配置
 *             autoindex_localtime on;
 *             root F:/; # 文件保存地址 --> root/file = BasePath配置
 *             autoindex off;
 *         }
 *     }
 *
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-18 23:33 <br>
 * @description: QiniuFileProcessService <br>
 */
@Slf4j
@Service("localFileProcessHandler")
public class LocalFileProcessHandler implements FileProcessHandler {

    // 配置code
    private final String BASE_PATH = "basePath";
    private final String DOMAIN = "domain";

    @Resource
    private FileRecordService fileRecordService;

    @Resource
    private ConfigDetailService configDetailService;

    @Override
    public FileProcess upload(FileProcessUploadDTO fileProcessUploadDTO, ModuleConfigWrapper fileConfigWrapper) {
        ConfigDetail configDetail = fileConfigWrapper.getConfigDetail();
        String basePath = configDetailService.getValueByCode(configDetail, BASE_PATH);
        String date = DateUtil.format(new Date(), "yyyy-MM-dd");
        basePath += basePath.charAt(basePath.length() - 1) == '/' ? date + "/" :  "/" + date + "/";
        String domain = configDetailService.getValueByCode(configDetail, DOMAIN);
        String key = fileProcessUploadDTO.getName();

        // 判断目录是否存在，不存在就创建目录
        File basePathFile = new File(basePath);
        if (!basePathFile.exists() || !basePathFile.isDirectory()) basePathFile.mkdirs();

        File newFile = new File(basePath + key);
        try (
                InputStream inputStream = fileProcessUploadDTO.getInputStream();
                OutputStream outputStream = new FileOutputStream(newFile);
        ) {
            IoUtil.copy(inputStream, outputStream);
        } catch (Exception e) {
            throw new ApiException(ResultStatus.FILE_UPLOAD_FAIL, e.getMessage());
        }
        String relativePath = String.join("/", date, key);

        // 保存记录
        FileRecord fileRecord = new FileRecord();
        BeanUtil.copyProperties(fileProcessUploadDTO, fileRecord);
        fileRecord.setRelativePath(relativePath);
        fileRecord.setSource(fileConfigWrapper.getConfig().getStorageType());
        fileRecordService.save(fileRecord);

        // 构建文件URL
        String fileUrl = domain.charAt(domain.length() - 1) == '/' ? domain + fileRecord.getId() : String.join("/", domain, fileRecord.getId().toString());

        // 返回结果
        return new FileProcess().setFileRecordId(fileRecord.getId()).setUrl(fileUrl);
    }
}
