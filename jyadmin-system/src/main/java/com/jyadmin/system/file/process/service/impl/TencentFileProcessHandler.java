package com.jyadmin.system.file.process.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.google.gson.Gson;
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
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-18 23:33 <br>
 * @description: QiniuFileProcessService <br>
 */
@Slf4j
@Service("tencentFileProcessHandler")
public class TencentFileProcessHandler implements FileProcessHandler {

    // 配置code
    private final String ACCESS_KEY = "ak";
    private final String SECRET_KEY = "sk";
    private final String BUCKET = "bucket";
    private final String DOMAIN = "domain";

    @Resource
    private FileRecordService fileRecordService;

    @Resource
    private ConfigDetailService configDetailService;

    @Override
    public FileProcess upload(FileProcessUploadDTO fileProcessUploadDTO, ModuleConfigWrapper fileConfigWrapper) {
        ConfigDetail configDetail = fileConfigWrapper.getConfigDetail();
        String accessKey = configDetailService.getValueByCode(configDetail, ACCESS_KEY);
        String secretKey = configDetailService.getValueByCode(configDetail, SECRET_KEY);
        String bucket = configDetailService.getValueByCode(configDetail, BUCKET);
        String domain = configDetailService.getValueByCode(configDetail, DOMAIN);
        String key = fileProcessUploadDTO.getName(); //默认不指定key的情况下，以文件内容的hash值作为文件名

        // 七牛云上传
        Configuration cfg = new Configuration();
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2; // 指定分片上传版本
        UploadManager uploadManager = new UploadManager(cfg);

        // 生成上传凭证，然后准备上传
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        String fileUrl = null;
        try {
            Response response = uploadManager.put(fileProcessUploadDTO.getInputStream(), key, upToken,null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            fileUrl = domain + putRet.key;
            log.debug("file key : {}, hash : {}, url : {}", putRet.key, putRet.hash, fileUrl);
        } catch (QiniuException ex) {
            throw new ApiException(ResultStatus.FILE_UPLOAD_FAIL, ex.getMessage());
        }

        // 保存记录
        FileRecord fileRecord = new FileRecord();
        BeanUtil.copyProperties(fileProcessUploadDTO, fileRecord);
        fileRecord.setPath(fileUrl);
        fileRecord.setSource(fileConfigWrapper.getConfig().getStorageType());
        fileRecordService.save(fileRecord);

        // 返回结果
        return new FileProcess().setFileRecordId(fileRecord.getId()).setUrl(fileRecord.getPath());
    }
}
