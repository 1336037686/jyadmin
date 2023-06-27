package com.jyadmin;


import com.jyadmin.util.AppInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication(scanBasePackages = {"com.jyadmin"})
public class AppRun {

    public static void main(String[] args) {
        ConfigurableApplicationContext app = SpringApplication.run(AppRun.class, args);
        AppInfoUtil.printAppInfo(app);
    }
}
