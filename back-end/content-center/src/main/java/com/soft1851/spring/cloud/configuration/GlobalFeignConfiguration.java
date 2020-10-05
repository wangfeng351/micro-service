package com.soft1851.spring.cloud.configuration;

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/30
 * @Version 1.0
 */
public class GlobalFeignConfiguration {
    @Bean
    public Logger.Level level() {
        //让Feign打印所有请求细节
        return Logger.Level.FULL;
    }
}
