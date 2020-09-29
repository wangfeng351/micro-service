package com.soft1851.spring.cloud.controller;

import com.soft1851.spring.cloud.common.ResponseResult;
import com.soft1851.spring.cloud.domain.dto.IdDto;
import com.soft1851.spring.cloud.domain.dto.PageDto;
import com.soft1851.spring.cloud.domain.entity.Share;
import com.soft1851.spring.cloud.service.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/24
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/share")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareInfoController {
    private  final ShareService shareService;

    /**
     * 查询所有的分享信息
     * @param pageDto
     * @return
     */
    @PostMapping(value = "/list")
    public ResponseResult getShareInfoList(@RequestBody PageDto pageDto) {
        return ResponseResult.success(shareService.getShareInfoList(pageDto));
    }

    /**
     * 查询分享详情
     * @param idDto
     * @return
     */
    @PostMapping(value = "/detail")
    public ResponseResult getShareInfoDetailById(@RequestBody IdDto idDto) {
        return ResponseResult.success(shareService.getShareVo(idDto.getId()));
    }
}
