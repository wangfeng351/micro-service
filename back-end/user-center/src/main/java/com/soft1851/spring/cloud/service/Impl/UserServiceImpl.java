package com.soft1851.spring.cloud.service.Impl;

import com.soft1851.spring.cloud.domain.dto.*;
import com.soft1851.spring.cloud.domain.entity.BonusEventLog;
import com.soft1851.spring.cloud.domain.entity.User;
import com.soft1851.spring.cloud.mapper.BonusEventLogMapper;
import com.soft1851.spring.cloud.mapper.UserMapper;
import com.soft1851.spring.cloud.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.time.DateFormatUtils;
import org.bouncycastle.util.Times;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
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
    public int reduceBonus(int bonus, int userId, String description) {
        System.out.println("进入该方法");
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        User user = userMapper.selectByPrimaryKey(userId);
        criteria.andEqualTo("id", userId);
        user.setBonus(user.getBonus() - bonus);
        //新增减分日志
        bonusEventLogMapper.insert(BonusEventLog.builder()
                .userId(userId)
                .value(-bonus)
                .event("CONTRIBUTE")
                .createTime(Timestamp.valueOf(LocalDateTime.now()))
                .description(description)
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

    @Override
    public User WxLogin(WxLoginDto wxLoginDto, String openId) {
        //先根据openId查找用户
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("wxId", openId);
        List<User> users = this.userMapper.selectByExample(example);
        System.out.println("获取到的信息是: >>>>>>>" + wxLoginDto);

        //没找到，是新用户，直接注册
        if (users.size() == 0) {
            User saveUser = User.builder()
                    .wxId(openId)
                    .account("")
                    .password("")
                    .avatarUrl(wxLoginDto.getAvatarUrl())
                    .wxNickname(wxLoginDto.getWxNickname())
                    .roles("user")
                    .bonus(100)
                    .createTime(Timestamp.valueOf(LocalDateTime.now()))
                    .updateTime(Timestamp.valueOf(LocalDateTime.now()))
                    .build();
            this.userMapper.insertSelective(saveUser);
            return saveUser;
        }
        System.out.println("返回数据是: " + users);
        return users.get(0);
    }

    @Override
    public UserDetailDto getUserInfoById(int userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        UserDetailDto userDetailDto = new UserDetailDto();
        BeanUtils.copyProperties(user, userDetailDto);
        System.out.println("获取到的用户数据是： " + userDetailDto);
        return userDetailDto;
    }

    @Override
    public int signIn(int userId) {
        //获取用户信息
        User user = userMapper.selectByPrimaryKey(userId);
        user.setBonus(user.getBonus() + 50);
        userMapper.updateByPrimaryKey(user);
        Example example = new Example(BonusEventLog.class);
        Example.Criteria criteria = example.createCriteria();
        String start = DateFormatUtils.format(new Date(), "yyyy-MM-dd 00:00:00");
        String end = DateFormatUtils.format(new Date(), "yyyy-MM-dd 23:59:59");
        criteria.andEqualTo("userId", userId).andBetween("createTime", Timestamp.valueOf(start), Timestamp.valueOf(end));
        BonusEventLog bonusEventLog = bonusEventLogMapper.selectOneByExample(example);
        if(bonusEventLog != null) {
            System.out.println("今日已签到");
            return 2;
        } else {
            return bonusEventLogMapper.insert(BonusEventLog.builder().userId(userId)
                    .description("签到").event("签到")
                    .value(50).createTime(Timestamp.valueOf(LocalDateTime.now())).build());
        }
    }
}
