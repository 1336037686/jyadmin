package com.jyadmin.consts;

import lombok.Data;

/**
 * 数据访问控制注解常量类
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-06-06 21:50 <br>
 * @description: DataAccessControlConstant <br>
 */
@Data
public class DataAccessControlConstant {

    // BaseMapper 默认方法
    public static final String BASE_MAPPER_DEFAULT_SELECT_BY_ID = "selectById";
    public static final String BASE_MAPPER_DEFAULT_SELECT_BATCH_IDS = "selectBatchIds";
    public static final String BASE_MAPPER_DEFAULT_SELECT_BY_MAP = "selectByMap";
    public static final String BASE_MAPPER_DEFAULT_SELECT_ONE = "selectOne";
    public static final String BASE_MAPPER_DEFAULT_SELECT_COUNT = "selectCount";
    public static final String BASE_MAPPER_DEFAULT_SELECT_LIST = "selectList";
    public static final String BASE_MAPPER_DEFAULT_SELECT_MAPS = "selectMaps";
    public static final String BASE_MAPPER_DEFAULT_SELECT_OBJS = "selectObjs";
    public static final String BASE_MAPPER_DEFAULT_SELECT_PAGE = "selectPage";
    public static final String BASE_MAPPER_DEFAULT_SELECT_MAPS_PAGE = "selectMapsPage";
    // BaseMapper 默认方法
    public static final String[] BASE_MAPPER_DEFAULT_SELECT_METHODS = new String[] {
            "selectById", "selectBatchIds",
            "selectByMap", "selectOne",
            "selectCount", "selectList",
            "selectMaps", "selectObjs",
            "selectPage", "selectMapsPage"
    };


}
