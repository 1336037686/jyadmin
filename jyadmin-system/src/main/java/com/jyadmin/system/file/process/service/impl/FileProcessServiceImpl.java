package com.jyadmin.system.file.process.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.jyadmin.consts.GlobalConstants;
import com.jyadmin.consts.ResultStatus;
import com.jyadmin.exception.ApiException;
import com.jyadmin.system.config.detail.domain.ConfigDetailJsonModel;
import com.jyadmin.system.config.module.domain.ModuleConfigWrapper;
import com.jyadmin.system.config.module.service.ModuleConfigService;
import com.jyadmin.system.file.manage.domain.FileRecord;
import com.jyadmin.system.file.process.domain.FileProcess;
import com.jyadmin.system.file.process.model.dto.FileProcessDownloadDTO;
import com.jyadmin.system.file.process.model.dto.FileProcessUploadDTO;
import com.jyadmin.system.file.process.service.FileProcessHandler;
import com.jyadmin.system.file.process.service.FileProcessService;
import com.jyadmin.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-18 23:50 <br>
 * @description: FileProcessServiceImpl <br>
 */
@Slf4j
@Service
public class FileProcessServiceImpl implements FileProcessService {

    @Resource
    private ModuleConfigService moduleConfigService;

    /**
     * 获取当前的附件配置
     * @return ConfigDetail 配置详情
     */
    private ModuleConfigWrapper getEnableFileConfigDetail() {
        return moduleConfigService.getEnableConfigDetail(GlobalConstants.SYS_FILE_CONFIG_ID);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public FileProcess upload(FileProcessUploadDTO fileProcessUploadDTO) {
        ModuleConfigWrapper fileConfigWrapper = getEnableFileConfigDetail();
        String storageType = fileConfigWrapper.getConfig().getStorageType();
        ConfigDetailJsonModel beanInfo = fileConfigWrapper.getConfigDetail().getJsonObjs().stream()
                .filter(x -> "bean".equals(x.getCode())).findFirst().orElse(null);
        Assert.notNull(beanInfo, "缺少Bean配置！");

        // 获取配置相对应的处理器
        FileProcessHandler fileProcessHandler = null;
        if ("qiniu-oss".equals(storageType)) fileProcessHandler = SpringUtil.getBean(beanInfo.getValue());
        if ("local-oss".equals(storageType)) fileProcessHandler = SpringUtil.getBean(beanInfo.getValue());
        if ("tencent-oss".equals(storageType)) fileProcessHandler = SpringUtil.getBean(beanInfo.getValue());
        if ("ali-oss".equals(storageType)) fileProcessHandler = SpringUtil.getBean(beanInfo.getValue());
        Assert.notNull(fileProcessHandler, "未找到相应处理器！");

        return fileProcessHandler.upload(fileProcessUploadDTO, fileConfigWrapper);
    }

    @Override
    public void download(FileProcessDownloadDTO fileProcessDownloadDTO) {
        FileRecord fileRecord = fileProcessDownloadDTO.getFileRecord();
        HttpServletResponse response = fileProcessDownloadDTO.getResponse();
        // 设置response为文件下载
        ResponseUtil.initFtpResponse(response);
        // 设置文件下载方式：附件下载
        response.setHeader("content-disposition", "attachment;fileName=" + URLEncoder.encode(fileRecord.getRealName()));
        // 执行状态
        int fetchIndex = 0;
        boolean fetchStatus = false;
        Exception exception = null;
        // 重试5次
        while (!fetchStatus && fetchIndex <= 5) {
            try {
                log.debug("file url: {}", fileProcessDownloadDTO.getFileRecord().getPath());
                URL url = new URL(fileProcessDownloadDTO.getFileRecord().getPath());
                URLConnection urlConnection = url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                OutputStream outputStream = response.getOutputStream();
                // 下载文件
                IOUtils.copy(inputStream, outputStream);
                fetchStatus = true;
            } catch (Exception e) {
                e.printStackTrace();
                fetchIndex += 1;
                exception = e;
            }
            if (!fetchStatus) {
                throw new ApiException(ResultStatus.FILE_DOWNLOAD_FAIL, exception.getMessage());
            }
        }
    }
}
