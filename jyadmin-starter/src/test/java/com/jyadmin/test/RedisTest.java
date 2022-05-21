package com.jyadmin.test;

import com.jyadmin.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-05-14 01:10 <br>
 * @description: RedisTest <br>
 */
@SpringBootTest
public class RedisTest {

    @Resource
    RedisUtil redisUtil;

    @Test
    public void test() {
        redisUtil.setValue("HelloWorld", "HelloWorld");
        String helloWorld = (String) redisUtil.getValue("HelloWorld");
        System.out.printf(helloWorld);
    }

}
