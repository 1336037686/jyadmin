package com.jyadmin.system.job.task;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-12-05 12:18 <br>
 * @description: TestTask <br>
 */
@Component("testTask")
public class TestTask {

    public void hello(Map params) {
        System.out.println("============ TestTask ===========");
        System.out.println(params);
        System.out.println("Hello TestTask: " + new Date());
        System.out.println("=================================");
    }

}
