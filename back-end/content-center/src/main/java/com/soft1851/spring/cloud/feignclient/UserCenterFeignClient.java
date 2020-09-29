package com.soft1851.spring.cloud.feignclient;

import com.soft1851.spring.cloud.domain.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/29
 * @Version 1.0
 */
//指定到对应的服务应用名上
@FeignClient("user-center")
public interface UserCenterFeignClient {

    /**
     * 根据用户id查询用户信息
     * @param id
     * @return
     */
    @GetMapping("/user/{id}")
    UserDto getUserDtoById(@PathVariable int id);


}
