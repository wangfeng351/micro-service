package com.soft1951.cloud.study;

import com.netflix.discovery.shared.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/10
 * @Version 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class EurekaClientMain {
    public static void main(String[] args) {
        SpringApplication.run(EurekaClientMain.class, args);
    }
}
