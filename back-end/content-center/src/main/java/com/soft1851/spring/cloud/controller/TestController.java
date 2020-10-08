package com.soft1851.spring.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.soft1851.spring.cloud.domain.dto.UserDto;
import com.soft1851.spring.cloud.feignclient.TestBaiduFeignClient;
import com.soft1851.spring.cloud.feignclient.TestInterface;
import com.soft1851.spring.cloud.feignclient.TestUserCenterFeignClient;
import com.soft1851.spring.cloud.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDate;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/30
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "test")
@ApiIgnore
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestController {
    private final TestUserCenterFeignClient testUserCenterFeignClient;
    private final TestBaiduFeignClient testBaiduFeignClient;
    private final TestInterface testInterface;
    private final TestService testService;

    @GetMapping(value = "/test-q")
    public UserDto query(@RequestParam int id, @RequestParam String wxNickname){
        UserDto userDto = UserDto.builder().id(id).wxNickname(wxNickname).build();
        return testUserCenterFeignClient.query(userDto);
    }

    @GetMapping(value = "/baidu")
    public String baiduIndex() {
        return testBaiduFeignClient.index();
    }

    @GetMapping("test-a")
    public String testA() {
        testService.commonMethod();
        return "test-a";
    }

    @GetMapping("test-b")
    public String testB() {
        testService.commonMethod();
        return "test-b";
    }

    @GetMapping("byResource")
    @SentinelResource(value = "hello", blockHandler = "handleException")
    public String byResource() {
        return "按名称限流";
    }


    public String handleException(BlockException blockException) {
        return "服务不可用";
    }

    @GetMapping(value = "/api")
    public String apiTest() {
        return testInterface.api();
    }

    @GetMapping("/do")
    public String log() {
        log.info("log4j2 test date: {}  info: {}", LocalDate.now(), "请关注公众号：Felordcn");
        return "log4j2";
    }
}

