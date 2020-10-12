package com.soft1851.spring.cloud.service;

import com.soft1851.spring.cloud.domain.entity.MidUserShare;
import com.soft1851.spring.cloud.domain.entity.Share;

import java.util.List;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/10/12
 * @Version 1.0
 */
public interface MidUserShareService {

    /**
     * 新增一条兑换信息
     * @param userId
     * @param bonus
     * @param shareId
     * @return
     */
    int insertMyExchange(Integer userId, Integer bonus, Integer shareId);

    /**
     * 通过用户id查询该用户兑换记录
     * @param userId
     * @return
     */
    List<MidUserShare> getMidUserSharesByUserId(int userId);
}
