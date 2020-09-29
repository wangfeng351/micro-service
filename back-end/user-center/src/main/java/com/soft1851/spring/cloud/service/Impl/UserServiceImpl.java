package com.soft1851.spring.cloud.service.Impl;

import com.soft1851.spring.cloud.domain.dto.UserDto;
import com.soft1851.spring.cloud.domain.entity.User;
import com.soft1851.spring.cloud.mapper.UserMapper;
import com.soft1851.spring.cloud.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/29
 * @Version 1.0
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    @Override
    public UserDto getUserById(int id) {
        System.out.println("业务逻辑层中的接口：" + id);
        User user = userMapper.selectByPrimaryKey(id);
        System.out.println("获取到的数据是>>>>>>>" + user);
        return UserDto.builder().wxNickname(user.getWxNickname()).build();
    }
}
