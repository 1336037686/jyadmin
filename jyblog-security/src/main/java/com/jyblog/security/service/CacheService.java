package com.jyblog.security.service;

import com.jyblog.security.domain.UserCacheInfo;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-05-10 23:19 <br>
 * @description: CacheService <br>
 */
public interface CacheService {

    boolean save(UserCacheInfo userCacheInfo);

    boolean isExist(String username);

    UserCacheInfo get(String username);

    boolean remove(String username);

}
