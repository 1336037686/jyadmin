package com.jyadmin.system.job.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-11-30 20:27 <br>
 * @description: JobApplicationRunner <br>
 */
@Slf4j
@Component
public class JobApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("========================== JobApplicationRunner START ===========================");
        log.info("========================== JobApplicationRunner END   ===========================");
    }

}
