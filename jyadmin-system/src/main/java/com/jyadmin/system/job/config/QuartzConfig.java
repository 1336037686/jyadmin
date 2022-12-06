package com.jyadmin.system.job.config;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**
 * 系统定时任务配置
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-30 10:51 <br>
 * @description: QuartzConfig <br>
 */
@Configuration
public class QuartzConfig {

    /**
     * 解决Job中注入Spring Bean为null的问题
     */
    @Component("quartzJobFactory")
    public static class QuartzJobFactory extends AdaptableJobFactory {
        private final AutowireCapableBeanFactory capableBeanFactory;

        public QuartzJobFactory(AutowireCapableBeanFactory capableBeanFactory) {
            this.capableBeanFactory = capableBeanFactory;
        }

        @Override
        protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
            // 调用父类的方法
            Object jobInstance = super.createJobInstance(bundle);
            // 进行注入
            capableBeanFactory.autowireBean(jobInstance);
            return jobInstance;
        }
    }

}
