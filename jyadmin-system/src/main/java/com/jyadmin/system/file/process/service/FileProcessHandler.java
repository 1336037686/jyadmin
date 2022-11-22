package com.jyadmin.system.file.process.service;

import com.jyadmin.system.config.module.domain.ModuleConfigWrapper;
import com.jyadmin.system.file.process.domain.FileProcess;
import com.jyadmin.system.file.process.model.dto.FileProcessUploadDTO;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-18 23:44 <br>
 * @description: FileProcessHandler <br>
 */
public interface FileProcessHandler {

    /**
     * 文件上传
     * @param fileProcessUploadDTO 文件上传DTO
     * @param fileConfigWrapper 所需配置
     * @return FileProcess
     */
    FileProcess upload(FileProcessUploadDTO fileProcessUploadDTO, ModuleConfigWrapper fileConfigWrapper);
}
