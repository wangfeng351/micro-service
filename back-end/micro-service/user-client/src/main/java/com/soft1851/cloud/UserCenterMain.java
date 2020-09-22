package com.soft1851.cloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/16
 * @Version 1.0
 */
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.soft1851.cloud.mapper")
public class UserCenterMain {
    public static void main(String[] args) {
        SpringApplication.run(UserCenterMain.class, args);
    }
}
