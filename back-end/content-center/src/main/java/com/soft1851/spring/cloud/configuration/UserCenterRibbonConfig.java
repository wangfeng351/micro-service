package com.soft1851.spring.cloud.configuration;

import center.configuration.RibbonConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Configuration;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/25
 * @Version 1.0
 */
@Configuration
//@RibbonClient(name = "user-center", configuration = RibbonConfiguration.class)
//全局配置自定义规则
@RibbonClients(defaultConfiguration = RibbonConfiguration.class)
public class UserCenterRibbonConfig {
}
