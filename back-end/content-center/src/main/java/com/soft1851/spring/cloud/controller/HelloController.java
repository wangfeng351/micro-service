package com.soft1851.spring.cloud.controller;

import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/22
 * @Version 1.0
 */
@RestController
@RequestMapping("/hello")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HelloController {
    private final DiscoveryClient discoveryClient;
    private final RestTemplate restTemplate;

    @GetMapping(value = "hello")
    public String testNacos() {
        return "Hello, Nacos!!!";
    }

    @GetMapping(value = "/test")
    public List<ServiceInstance> test() {
        return discoveryClient.getInstances("course-center");
    }

    @GetMapping("/call/example")
    public String getExample() {
        List<ServiceInstance> instances = discoveryClient.getInstances("user-center");
        String targetUrl = instances.get(0).getUri().toString();
        return restTemplate.getForObject(targetUrl, String.class);
    }

    @GetMapping("/call/test")
    public String callTest() {
        Random random = new Random();
        //用户中心实例的信息
        List<ServiceInstance> instances = discoveryClient.getInstances("user-center");
        //stream编程、Lambda表达式、函数式编程
        String targetUrl = instances.stream()
                .map(instance -> instance.getUri().toString() + "/hello")
                //ThreadLocalRandom 获取唯一的随机数
                .collect(Collectors.toList()).get(ThreadLocalRandom.current().nextInt(instances.size()));
        log.info("提供者的url是>>>>>>>>" + targetUrl);
        return restTemplate.getForObject(targetUrl, String.class);
    }

    @GetMapping("call/ribbon")
    public String callRibbon() {
        return restTemplate.getForObject("http://user-center:8006/hello", String.class);
    }
}
