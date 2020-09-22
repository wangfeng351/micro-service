package com.soft1851.cloud.controller;

import com.soft1851.cloud.entity.TestUser;
import com.soft1851.cloud.mapper.TestUserMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/16
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "user")
public class TestUserController {
    @Resource
    private TestUserMapper testUserMapper;

    @GetMapping(value = "info/{id}")
    public TestUser getUserById(@PathVariable int id){
        return testUserMapper.getUserById(id);
    }

    @GetMapping()
    public String get() {
        return "hello";
    }
}
