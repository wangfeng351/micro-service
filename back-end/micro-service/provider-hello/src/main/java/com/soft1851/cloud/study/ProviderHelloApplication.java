package com.soft1851.cloud.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/13
 * @Version 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ProviderHelloApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderHelloApplication.class, args);
    }
}
