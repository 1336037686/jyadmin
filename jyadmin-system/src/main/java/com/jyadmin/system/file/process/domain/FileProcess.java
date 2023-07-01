package com.jyadmin.system.file.process.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-17 23:00 <br>
 * @description: FileProcess <br>
 */
@Data
@Accessors(chain = true)
public class FileProcess {

    /**
     * 文件记录ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long fileRecordId;

    /**
     * 文件url地址
     */
    private String url;

}
