package com.jyadmin;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = {"com.jyadmin"})
public class AppRun {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(AppRun.class, args);
    }

}
