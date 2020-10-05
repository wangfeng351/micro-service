package com.soft1851.spring.cloud.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Description 调用远程接口
 * @Author wf
 * @Date 2020/9/30
 * @Version 1.0
 */
@FeignClient(name = "interface", url = "http://47.111.64.183:8006/user/1")
public interface TestInterface {

    /**
     * 返回远程接口的数据
     * @return
     */
    @GetMapping("")
    String api();
}
