package com.jyadmin.system.file.process.model.dto;

import com.jyadmin.system.file.manage.domain.FileRecord;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.servlet.http.HttpServletResponse;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-20 05:21 <br>
 * @description: FileProcessDownloadDTO <br>
 */
@Data
@Accessors(chain = true)
public class FileProcessDownloadDTO {

    private FileRecord fileRecord;

    private HttpServletResponse response;

}
