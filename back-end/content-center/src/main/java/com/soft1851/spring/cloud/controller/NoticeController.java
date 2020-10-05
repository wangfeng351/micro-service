package com.soft1851.spring.cloud.controller;

import com.soft1851.spring.cloud.domain.entity.Notice;
import com.soft1851.spring.cloud.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/10/4
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/notice")
//Resources中的二级标题
@Api(tags = "公告接口", value = "提供公告信息相关的Rest API")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NoticeController {
    private final NoticeService noticeService;

    //接口的描述
    @ApiOperation(value = "查询最新公告消息", notes = "查询最新公告消息")
    @GetMapping(value = "/last")
    public Notice getLastNotice() {
        return noticeService.getLastNotice();
    }

}
