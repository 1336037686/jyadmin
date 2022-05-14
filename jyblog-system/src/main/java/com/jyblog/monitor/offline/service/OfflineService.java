package com.jyblog.monitor.offline.service;


import com.jyblog.domain.UserCacheInfo;
import com.jyblog.monitor.offline.model.vo.UserQueryVO;

import java.util.List;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-05-13 22:39 <br>
 * @description: OfflineService <br>
 */
public interface OfflineService {

    List<UserCacheInfo> getList(UserQueryVO vo);

    void forcedOffline(String username);
}
