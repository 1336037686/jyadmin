package com.jyadmin.system.file.config.service;

import com.jyadmin.system.config.detail.domain.ConfigDetail;

import java.util.List;

/**
* @author 13360
* @description 针对表【sys_file_config(附件基础配置)】的数据库操作Service
* @createDate 2022-11-16 00:02:03
*/
public interface FileConfigService {

    List<ConfigDetail> getConfigListByCode(String code);
}
