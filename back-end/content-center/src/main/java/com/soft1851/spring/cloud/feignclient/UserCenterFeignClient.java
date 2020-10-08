package com.soft1851.spring.cloud.feignclient;

import com.soft1851.spring.cloud.configuration.GlobalFeignConfiguration;
import com.soft1851.spring.cloud.configuration.UserCenterFeignConfiguration;
import com.soft1851.spring.cloud.domain.dto.UserAddBonusMsgDTO;
import com.soft1851.spring.cloud.domain.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/29
 * @Version 1.0
 */
//指定到对应的服务应用名上
@FeignClient(value = "user-center", configuration = UserCenterFeignConfiguration.class)
//@FeignClient(value = "user-center")
public interface UserCenterFeignClient {

    /**
     * 根据用户id查询用户信息
     * @param id
     * @return
     */
    @GetMapping("/user/{id}")
    UserDto getUserDtoById(@PathVariable int id);

    /**
     * 根据用户id查询用户信息
     * @param userAddBonusMsgDTO
     * @return
     */
    @PutMapping("/user/update/bonus")
    UserDto updateUserBonusByUserId(@RequestBody UserAddBonusMsgDTO userAddBonusMsgDTO);
}
