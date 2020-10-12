package com.soft1851.spring.cloud.service.Impl;

import com.soft1851.spring.cloud.domain.entity.MidUserShare;
import com.soft1851.spring.cloud.feignclient.UserCenterFeignClient;
import com.soft1851.spring.cloud.mapper.MidUserShareMapper;
import com.soft1851.spring.cloud.service.MidUserShareService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/10/12
 * @Version 1.0
 */
@Service
public class MidUserShareServiceImpl implements MidUserShareService {
    @Resource
    private MidUserShareMapper midUserShareMapper;
    @Resource
    private UserCenterFeignClient userCenterFeignClient;

    @Override
    public int insertMyExchange(Integer userId, Integer bonus, Integer shareId) {
        MidUserShare midUserShare = MidUserShare.builder().userId(userId).shareId(shareId).build();
        //异步更新用户积分
        updateUserBonus(bonus, userId);
        return midUserShareMapper.insert(midUserShare);
    }

    @Override
    public List<MidUserShare> getMidUserSharesByUserId(int userId) {
        return null;
    }

    @Async
    public void updateUserBonus(int bonus, int userId) {
        //更改积分
        userCenterFeignClient.reduceBonus(bonus, userId);
    }
}
