package com.soft1951.cloud.study.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/10
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/test")
public class TestController {
    @GetMapping()
    public String getTestResult() {
        return "I am testing service register";
    }
}
