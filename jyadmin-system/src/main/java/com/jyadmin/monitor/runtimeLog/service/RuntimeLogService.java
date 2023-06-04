package com.jyadmin.monitor.runtimeLog.service;

import com.jyadmin.monitor.runtimeLog.model.vo.RuntimeLogDirResVO;
import com.jyadmin.monitor.runtimeLog.model.vo.RuntimeLogReqVO;

import java.util.List;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-06-04 01:19 <br>
 * @description: RuntimeLogService <br>
 */
public interface RuntimeLogService {

    List<RuntimeLogDirResVO> getRuntimeLogDir();

    String getRuntimeLogContent(RuntimeLogReqVO runtimeLogVO);

}
