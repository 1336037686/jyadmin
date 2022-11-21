package com.jyadmin.system.file.process.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.InputStream;

/**
 * 文件上传DTO
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-17 22:44 <br>
 * @description: FileProcessVO <br>
 */
@Data
@Accessors(chain = true)
public class FileProcessUploadDTO {

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 文件后缀
     */
    private String suffix;

    /**
     * 原文件名称
     */
    private String realName;

    /**
     * 业务标识
     */
    private String relevance;

    /**
     * 文件输入流
     */
    private InputStream inputStream;
}
