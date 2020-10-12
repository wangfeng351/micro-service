package com.soft1851.spring.cloud.service;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/10/12
 * @Version 1.0
 */
public interface BonusEventLogService {

    /**
     * 新增日志事件
     * @return
     */
    int insertBonusEventLog(int userId, int bonus);
}
