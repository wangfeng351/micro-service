package com.soft1851.spring.cloud.service;

import com.soft1851.spring.cloud.domain.dto.LoginDto;
import com.soft1851.spring.cloud.domain.dto.UserDto;
import com.soft1851.spring.cloud.domain.entity.User;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/29
 * @Version 1.0
 */
public interface UserService {

    /**
     * 根据id获取用户信息
     * @param id
     * @return
     */
    UserDto getUserById(int id);

    /**
     * 登录接口
     * @param loginDto
     * @return
     */
    User login(LoginDto loginDto);
}
