package com.soft1851.spring.cloud.service;

import com.soft1851.spring.cloud.domain.entity.Notice;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/10/4
 * @Version 1.0
 */
public interface NoticeService {

    /**
     * 获取最新的通知
     *
     * @return
     */
    Notice getLastNotice();
}
