package com.soft1851.spring.cloud.service.Impl;

import com.soft1851.spring.cloud.domain.dto.LoginDto;
import com.soft1851.spring.cloud.domain.dto.UserAddBonusMsgDTO;
import com.soft1851.spring.cloud.domain.dto.UserDto;
import com.soft1851.spring.cloud.domain.entity.BonusEventLog;
import com.soft1851.spring.cloud.domain.entity.User;
import com.soft1851.spring.cloud.mapper.BonusEventLogMapper;
import com.soft1851.spring.cloud.mapper.UserMapper;
import com.soft1851.spring.cloud.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

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
    private final BonusEventLogMapper bonusEventLogMapper;

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
        if (user.getPassword().equals(loginDto.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public int updateBonus(UserAddBonusMsgDTO userAddBonusMsgDTO) {
        User user = userMapper.selectByPrimaryKey(userAddBonusMsgDTO.getUserId());
        System.out.println("修改用户积分：" + userAddBonusMsgDTO.getBounds());
        user.setBonus(user.getBonus() + userAddBonusMsgDTO.getBounds());
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int reduceBonus(int bonus, int userId) {
        System.out.println("进入该方法");
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        User user = userMapper.selectByPrimaryKey(userId);
        criteria.andEqualTo("id", userId);
        user.setBonus(user.getBonus() - bonus);
        //新增减分日志
        bonusEventLogMapper.insert(BonusEventLog.builder()
                .userId(userId)
                .value(bonus)
                .event("CONTRIBUTE")
                .createTime(Timestamp.valueOf(LocalDateTime.now()))
                .description("下载分享扣分")
                .build());
        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public List<BonusEventLog> getBonusListByUserId(int userId) {
        Example example = new Example(BonusEventLog.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        return bonusEventLogMapper.selectByExample(example);
    }
}
