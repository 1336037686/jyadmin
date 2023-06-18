package com.jyadmin.monitor.runtimeLog.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.util.ArrayUtil;
import com.google.common.collect.Lists;
import com.jyadmin.config.properties.JyRuntimeLogProperties;
import com.jyadmin.consts.GlobalConstants;
import com.jyadmin.monitor.runtimeLog.model.vo.RuntimeLogDirResVO;
import com.jyadmin.monitor.runtimeLog.model.vo.RuntimeLogReqVO;
import com.jyadmin.monitor.runtimeLog.service.RuntimeLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-06-04 01:19 <br>
 * @description: RuntimeLogServiceImpl <br>
 */
@Slf4j
@Service
public class RuntimeLogServiceImpl implements RuntimeLogService {

    @Resource
    private JyRuntimeLogProperties jyRuntimeLogProperties;

    @Override
    public List<RuntimeLogDirResVO> getRuntimeLogDir() {
        if (StringUtils.isBlank(jyRuntimeLogProperties.getBasePath())) {
            log.error("The configured log directory is empty.");
            return Lists.newArrayList();
        }
        File basePathFile = new File(jyRuntimeLogProperties.getBasePath());
        if (Boolean.FALSE.equals(FileUtil.exist(basePathFile))) {
            log.error("The configured log directory not exist.");
            return Lists.newArrayList();
        }
        if (Boolean.FALSE.equals(basePathFile.isDirectory())) {
            log.error("{} is not directory!.", jyRuntimeLogProperties.getBasePath());
            return Lists.newArrayList();
        }
        File[] files = basePathFile.listFiles();
        if (ArrayUtil.isEmpty(files)) return Lists.newArrayList();

        List<RuntimeLogDirResVO> runtimeLogDirResVOS = convertFilesToRes(files);
        log.info("logs dir tree: {}", runtimeLogDirResVOS);
        return runtimeLogDirResVOS;
    }

    @Override
    public String getRuntimeLogContent(RuntimeLogReqVO runtimeLogVO) {
        if (GlobalConstants.SYS_RUNTIME_LOG_TYPE_DIR.equals(runtimeLogVO.getType())) {
            log.error("{} is not log file！", runtimeLogVO.getPath());
            return null;
        }
        FileReader fileReader = new FileReader(runtimeLogVO.getPath());
        String result = fileReader.readString();
        return result;
    }

    /**
     * 将file转换为RuntimeLogDirResVO
     * @param files /
     * @return list
     */
    private List<RuntimeLogDirResVO> convertFilesToRes(File[] files) {
        List<RuntimeLogDirResVO> res = new ArrayList<>();
        Arrays.stream(files).forEach(file -> {
            RuntimeLogDirResVO vo = new RuntimeLogDirResVO()
                    .setId(UUID.randomUUID().toString().replace("-", ""))
                    .setName(file.getName())
                    .setPath(file.getPath());
            if (file.isDirectory()) {
                vo.setType(GlobalConstants.SYS_RUNTIME_LOG_TYPE_DIR);
                File[] childrenFiles = file.listFiles();
                if (ArrayUtil.isNotEmpty(childrenFiles)) vo.setChildren(convertFilesToRes(childrenFiles));
            } else {
                vo.setType(GlobalConstants.SYS_RUNTIME_LOG_TYPE_FILE);
            }
            res.add(vo);
        });
        return res;
    }
}
