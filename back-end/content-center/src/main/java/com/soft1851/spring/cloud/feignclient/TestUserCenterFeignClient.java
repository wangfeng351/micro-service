package com.soft1851.spring.cloud.feignclient;

import com.soft1851.spring.cloud.domain.dto.UserDto;
import org.apache.catalina.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/30
 * @Version 1.0
 */
@FeignClient(name = "user-center")
public interface TestUserCenterFeignClient {

    /**
     * 多参数查询
     * @param userDto
     * @return
     */
    @GetMapping("/user/q")
    UserDto query(@SpringQueryMap UserDto userDto);

    @GetMapping("/user/q")
    UserDto query1(@RequestParam String wxNickname, @RequestParam int id);
}
