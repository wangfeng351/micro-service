package com.soft1851.spring.cloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/22
 * @Version 1.0
 */
@RestController
@Slf4j
public class Test {
    @GetMapping(value = "hello")
    public String testNacos() {
        log.info("我被调用了");
        return "good afternoon, Nacos!!!";
    }
}
