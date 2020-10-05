package com.soft1851.spring.cloud.service.Impl;

import com.soft1851.spring.cloud.domain.dto.LoginDto;
import com.soft1851.spring.cloud.domain.dto.UserDto;
import com.soft1851.spring.cloud.domain.entity.User;
import com.soft1851.spring.cloud.mapper.UserMapper;
import com.soft1851.spring.cloud.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

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

    @Override
    public User login(LoginDto loginDto) {
        Example example = new Example(User.class);
        example.setOrderByClause("create_time DESC");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("account", loginDto.getAccount());
        User user = userMapper.selectOneByExample(example);
        if(user.getPassword().equals(loginDto.getPassword())) {
            return user;
        }
        return null;
    }
}
