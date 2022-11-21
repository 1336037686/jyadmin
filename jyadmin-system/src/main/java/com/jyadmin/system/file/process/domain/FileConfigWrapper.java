package com.jyadmin.system.file.process.domain;

import com.jyadmin.system.config.detail.domain.ConfigDetail;
import com.jyadmin.system.file.config.domain.FileConfig;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-19 02:20 <br>
 * @description: FileConfigWrapper <br>
 */
@Data
@Accessors(chain = true)
public class FileConfigWrapper {

    /**
     * 附件配置
     */
    private FileConfig config;

    /**
     * 配置详情
     */
    private ConfigDetail configDetail;

}
