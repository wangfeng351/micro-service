package com.soft1851.spring.cloud.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/10/6
 * @Version 1.0
 */
@Service
@Slf4j
public class TestService {
    @SentinelResource("common")

    public String commonMethod() {
        log.info("commonMethod...");
        return "common";
    }
}
