package com.soft1851.spring.cloud.controller;

import com.soft1851.spring.cloud.domain.dto.UserDto;
import com.soft1851.spring.cloud.feignclient.TestBaiduFeignClient;
import com.soft1851.spring.cloud.feignclient.TestInterface;
import com.soft1851.spring.cloud.feignclient.TestUserCenterFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/30
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "test")
@ApiIgnore
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestController {
    private final TestUserCenterFeignClient testUserCenterFeignClient;
    private final TestBaiduFeignClient testBaiduFeignClient;
    private final TestInterface testInterface;

    @GetMapping(value = "/test-q")
    public UserDto query(@RequestParam int id, @RequestParam String wxNickname){
        UserDto userDto = UserDto.builder().id(id).wxNickname(wxNickname).build();
        return testUserCenterFeignClient.query(userDto);
    }

    @GetMapping(value = "/baidu")
    public String baiduIndex() {
        return testBaiduFeignClient.index();
    }

    @GetMapping(value = "/api")
    public String apiTest() {
        return testInterface.api();
    }
}

