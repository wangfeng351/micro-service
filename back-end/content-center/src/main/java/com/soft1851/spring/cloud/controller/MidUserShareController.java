package com.soft1851.spring.cloud.controller;

import com.soft1851.spring.cloud.common.ResponseResult;
import com.soft1851.spring.cloud.service.MidUserShareService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/10/12
 * @Version 1.0
 */
@RequestMapping(value = "exchange")
@RestController
@ApiOperation(value = "我的兑换")
public class MidUserShareController {
    @Resource
    private MidUserShareService userShareService;


    @PostMapping()
    public ResponseResult insert(@RequestParam Integer userId,@RequestParam Integer bonus, @RequestParam Integer shareId) {
        return ResponseResult.success(userShareService.insertMyExchange(userId, bonus, shareId));
    }
}
