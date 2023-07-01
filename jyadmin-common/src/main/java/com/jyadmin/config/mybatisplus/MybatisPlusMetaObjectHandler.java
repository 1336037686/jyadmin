package com.jyadmin.config.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.jyadmin.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MybatisPlus 自动填充配置
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-04-04 22:55 <br>
 * @description: JyMybatisPlusMetaObjectHandler <br>
 */
@Slf4j
@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Long currentUserId = null;
        try {
            currentUserId = SecurityUtil.getCurrentUserId();
        } catch (Exception ignored) {}

        this.strictInsertFill(metaObject, "createBy", Long.class, currentUserId); // 起始版本 3.3.0(推荐使用)
        this.strictInsertFill(metaObject, "updateBy", Long.class, currentUserId); // 起始版本 3.3.0(推荐使用)

        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long currentUserId = null;
        try {
            currentUserId = SecurityUtil.getCurrentUserId();
        } catch (Exception ignored) {}

        this.strictUpdateFill(metaObject, "updateBy", Long.class, currentUserId); // 起始版本 3.3.0(推荐使用)
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class,  LocalDateTime.now()); // 起始版本 3.3.0(推荐)
    }

}
