package com.soft1851.spring.cloud.controller;

import com.soft1851.spring.cloud.domain.dto.UserDto;
import com.soft1851.spring.cloud.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/29
 * @Version 1.0
 */
@RequestMapping(value = "user")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;

    @GetMapping(value = "{id}")
    public UserDto getUserDtoById(@PathVariable int id){
        System.out.println("获取到的数据是》》" + id);
        return userService.getUserById(id);
    }
}
