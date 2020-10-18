package com.fanruan.platform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.fanruan.*")
@MapperScan("com.fanruan.platform.mapper")
public class CreditSharingPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditSharingPlatformApplication.class, args);
    }
}
