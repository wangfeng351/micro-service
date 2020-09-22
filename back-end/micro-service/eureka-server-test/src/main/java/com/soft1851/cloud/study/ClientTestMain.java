package com.soft1851.cloud.study;
import org.mybatis.spring.annotation.MapperScan;
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
@MapperScan("com.soft1851.cloud.study.mapper")
public class ClientTestMain {
    public static void main(String[] args) {
        SpringApplication.run(ClientTestMain.class, args);
    }
}
