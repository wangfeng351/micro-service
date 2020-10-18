package com.soft1851.spring.cloud.service;

import com.soft1851.spring.cloud.domain.dto.*;
import com.soft1851.spring.cloud.domain.entity.BonusEventLog;
import com.soft1851.spring.cloud.domain.entity.User;

import java.util.List;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/29
 * @Version 1.0
 */
public interface UserService {

    /**
     * 根据id获取用户信息
     *
     * @param id
     * @return
     */
    UserDto getUserById(int id);

    /**
     * 登录接口
     *
     * @param loginDto
     * @return
     */
    FlLoginDto login(LoginDto loginDto);

    /**
     * 修改积分
     *
     * @return
     */
    int updateBonus(UserAddBonusMsgDTO userAddBonusMsgDTO);

    /**
     * 减少积分
     * @param bonus
     * @return
     */
    int reduceBonus(int bonus, int userId, String description);

    /**
     * 获取用户积分列表
     * @param userId
     * @return
     */
    List<BonusEventLog> getBonusListByUserId(int userId);

    /**
     * 小程序登录
     * @param wxLoginDto
     * @param openId
     * @return
     */
    User WxLogin(WxLoginDto wxLoginDto, String openId);

    /**
     * 根据id获取用户信息
     * @param userId
     * @return
     */
    UserDetailDto getUserInfoById(int userId);

    /**
     * 用户签到
     * @return
     */
    int signIn(int userId);
}
