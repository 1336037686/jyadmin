package com.jyadmin.config.mybatisplus;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.jyadmin.annotation.DataAccessControl;
import com.jyadmin.util.ThrowableUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;

/**
 * 数据权限拦截器
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-06-06 16:38 <br>
 * @description: DataAccessControllerInterceptor <br>
 */
@Slf4j
@Data
@NoArgsConstructor
@SuppressWarnings({"rawtypes"})
public class DataAccessControllerInterceptor implements InnerInterceptor {

    @Override
    public boolean willDoQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        ExecMapperMethodInfo mapperMethodInfo = this.getExecMapperMethodInfo(ms);
        if (Boolean.FALSE.equals(this.needExecuteInerceptor(mapperMethodInfo))) {
            return InnerInterceptor.super.willDoQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
        }
        return InnerInterceptor.super.willDoQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
    }

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        ExecMapperMethodInfo mapperMethodInfo = this.getExecMapperMethodInfo(ms);
        if (Boolean.FALSE.equals(this.needExecuteInerceptor(mapperMethodInfo))) {
            InnerInterceptor.super.beforeQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
            return;
        }
        String originalSql = boundSql.getSql();
        Object parameterObject = boundSql.getParameterObject();
        String newSql = "SELECT * FROM (" + originalSql + ") T WHERE T.deleted=0 AND T.id = 100";
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), newSql, boundSql.getParameterMappings(), parameterObject);
        // 将修改后的SQL语句打印出来，以便于调试
        log.debug("new sql: {}", newSql);
        PluginUtils.mpBoundSql(boundSql).sql(newSql);
    }


    /**
     * 获取当前执行的Mapper方法信息
     * @param ms MappedStatement
     * @return ExecMapperMethodInfo
     */
    public ExecMapperMethodInfo getExecMapperMethodInfo(MappedStatement ms) {
        String id = ms.getId();
        // 获取类名
        String className = id.substring(0, id.lastIndexOf("."));
        // 获取方法名
        String methodName = id.substring(id.lastIndexOf(".") + 1);
        Class<?> clazz = null; Method method = null;
        DataAccessControl classDataAccessControl = null;
        DataAccessControl fieldDataAccessControl = null;
        try {
            // 获取当前mapper的 class
            clazz = Class.forName(className);
            // 获取当前mapper 上权限控制注解
            classDataAccessControl = clazz.getAnnotation(DataAccessControl.class);
            // 获取当前执行的mapper方法
            method = Arrays.stream(clazz.getMethods()).filter(x -> x.getName().equals(methodName)).findFirst().orElse(null);
            // 获取当前执行mapper方法上面的权限控制注解
            if (Objects.nonNull(method)) fieldDataAccessControl = method.getAnnotation(DataAccessControl.class);
            // 判断是否需要数据范围控制
            Boolean hasDataAccess = false;
            // 如果当前mapper类存在权限控制注解，且当前执行mapper方法在注解内部定义方法内部，则表示需要数据权限控制
            if (Objects.nonNull(classDataAccessControl)) {
                String[] methods = classDataAccessControl.methods();
                if (ArrayUtil.isNotEmpty(methods))  hasDataAccess = Arrays.stream(methods).anyMatch(methodName::equals);
            }
            // 如果当前执行mapper方法上面存在权限控制注解，则表示需要数据权限控制
            if (Boolean.FALSE.equals(hasDataAccess)) hasDataAccess = Objects.nonNull(fieldDataAccessControl);

            // 返回数据
            return new ExecMapperMethodInfo().setSuccess(true)
                    .setClazz(clazz).setClassDataAccessControl(classDataAccessControl)
                    .setMethod(method).setFieldDataAccessControl(fieldDataAccessControl)
                    .setHasDataAccessControl(hasDataAccess);
        } catch (ClassNotFoundException e) {
            log.error(ThrowableUtil.getStackTrace(e));
        }
        return new ExecMapperMethodInfo().setSuccess(false);
    }

    /**
     * 是否需要执行拦截器
     * @param executorMethodInfo 当前mapper基础信息
     * @return /
     */
    private boolean needExecuteInerceptor(ExecMapperMethodInfo executorMethodInfo) {
        if (!executorMethodInfo.getSuccess()) return false;
        return executorMethodInfo.getHasDataAccessControl();
    }

    @Accessors(chain = true)
    @Data
    public static class ExecMapperMethodInfo {

        // 获取方法参数状态
        private Boolean success;

        // 当前类
        private Class<?> clazz;

        // 类数据权限注解
        private DataAccessControl classDataAccessControl;

        // 当前方法
        private Method method;

        // 属性权限注解
        private DataAccessControl fieldDataAccessControl;

        // 当前mapper方法是否需要数据校验
        private Boolean hasDataAccessControl;

    }

}
