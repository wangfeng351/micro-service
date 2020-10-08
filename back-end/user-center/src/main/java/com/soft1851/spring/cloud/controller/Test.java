package com.soft1851.spring.cloud.controller;

import com.soft1851.GetHashCodeClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/22
 * @Version 1.0
 */
@RestController
@Slf4j

public class Test {
    @Resource
    private GetHashCodeClass getHashCodeClass;

    @GetMapping(value = "hello")
    public String testNacos() {
        log.info("我被调用了");
        System.out.println(getHashCodeClass.hashCode());
        return "good afternoon, Nacos!!!";
    }
}
