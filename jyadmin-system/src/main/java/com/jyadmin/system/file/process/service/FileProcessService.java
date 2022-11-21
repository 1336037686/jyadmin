package com.jyadmin.system.file.process.service;

import com.jyadmin.system.file.process.domain.FileProcess;
import com.jyadmin.system.file.process.model.dto.FileProcessDownloadDTO;
import com.jyadmin.system.file.process.model.dto.FileProcessUploadDTO;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-17 22:47 <br>
 * @description: FileProcessService <br>
 */
public interface FileProcessService {


    /**
     * 文件上传
     * @param fileProcessUploadDTO 文件上传DTO
     * @return FileProcess
     */
    FileProcess upload(FileProcessUploadDTO fileProcessUploadDTO);

    /**
     * 文件下载
     * @param fileProcessDownloadDTO 文件下载DTO
     */
    void download(FileProcessDownloadDTO fileProcessDownloadDTO);


}
