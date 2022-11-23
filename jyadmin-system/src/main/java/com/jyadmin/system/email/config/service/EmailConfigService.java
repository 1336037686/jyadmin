package com.jyadmin.system.email.config.service;

import com.jyadmin.system.config.detail.domain.ConfigDetail;

import java.util.List;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-23 23:10 <br>
 * @description: EmailConfigService <br>
 */
public interface EmailConfigService {

    List<ConfigDetail> getConfigListByCode(String code);

}
