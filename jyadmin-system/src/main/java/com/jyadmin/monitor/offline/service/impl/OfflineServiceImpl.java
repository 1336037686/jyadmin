package com.jyadmin.monitor.offline.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jyadmin.config.properties.JyAuthProperties;
import com.jyadmin.domain.UserCacheInfo;
import com.jyadmin.monitor.offline.model.vo.UserQueryVO;
import com.jyadmin.monitor.offline.service.OfflineService;
import com.jyadmin.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-05-13 22:40 <br>
 * @description: OfflineServiceImpl <br>
 */
@Service
public class OfflineServiceImpl implements OfflineService {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private JyAuthProperties jyAuthProperties;

    @Override
    public List<UserCacheInfo> getList(UserQueryVO vo) {
        Set<String> keys = redisTemplate.keys(jyAuthProperties.getAuthUserPrefix() + ":*");
        List<UserCacheInfo> record = new ArrayList<>();
        for (String key : keys) {
            try {
                UserCacheInfo userCacheInfo = new ObjectMapper().readValue(JSON.toJSONString(redisUtil.getValue(key)), UserCacheInfo.class);
                record.add(userCacheInfo);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return record.stream().filter(x ->
                !StringUtils.isNotBlank(vo.getUsername()) || x.getUsername().contains(vo.getUsername())
        ).collect(Collectors.toList());
    }

    @Override
    public void forcedOffline(String username) {
        String key = jyAuthProperties.getAuthUserPrefix() + ":" + username;
        if (redisUtil.exists(key)) {
            redisUtil.delete(key);
        }
    }
}
