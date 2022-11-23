package com.jyadmin.system.email.config.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jyadmin.system.config.detail.domain.ConfigDetail;
import com.jyadmin.system.config.detail.domain.ConfigDetailJsonModel;
import com.jyadmin.system.config.detail.service.ConfigDetailService;
import com.jyadmin.system.config.template.domain.ConfigTemplate;
import com.jyadmin.system.config.template.service.ConfigTemplateService;
import com.jyadmin.system.email.config.service.EmailConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-23 23:11 <br>
 * @description: EmailConfigServiceImpl <br>
 */
@Slf4j
@Service
public class EmailConfigServiceImpl implements EmailConfigService {

    @Resource
    private ConfigTemplateService configTemplateService;

    @Resource
    private ConfigDetailService configDetailService;

    @Override
    public List<ConfigDetail> getConfigListByCode(String code) {
        String realCode = code.toUpperCase().replace("-", "_") + "_CONFIG";
        ConfigTemplate configTemplate = configTemplateService.getOne(new LambdaQueryWrapper<ConfigTemplate>().eq(true, ConfigTemplate::getCode, realCode));
        List<ConfigDetail> configDetails = configDetailService.list(new LambdaQueryWrapper<ConfigDetail>()
                .eq(true, ConfigDetail::getTemplateId, configTemplate.getId())
                .eq(true, ConfigDetail::getStatus, 1)
        );
        List<ConfigDetail> newConfigDetails = configDetails.stream().peek(x -> x.setJsonObjs(
                StringUtils.isBlank(x.getData()) ? new ArrayList<>() :
                        JSON.parseArray(x.getData(), ConfigDetailJsonModel.class)
                                .stream().sorted(Comparator.comparing(ConfigDetailJsonModel::getSort))
                                .collect(Collectors.toList())
        )).collect(Collectors.toList());
        return newConfigDetails;
    }
}
