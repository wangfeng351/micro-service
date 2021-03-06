package com.soft1851.spring.cloud.controller;

import com.soft1851.spring.cloud.common.ResponseResult;
import com.soft1851.spring.cloud.domain.dto.ExchangeDto;
import com.soft1851.spring.cloud.domain.dto.IdDto;
import com.soft1851.spring.cloud.domain.dto.PageDto;
import com.soft1851.spring.cloud.domain.dto.ShareRequestDto;
import com.soft1851.spring.cloud.service.ShareService;
import com.soft1851.spring.cloud.util.JwtOperator;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.faces.annotation.RequestParameterMap;
import java.util.List;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/24
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/share")
@Api(tags = "分享接口", value = "提供分享信息相关的Rest API")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareInfoController {
    private final ShareService shareService;
    private final JwtOperator jwtOperator;
    /**
     * 查询所有的分享信息
     *
     * @param pageDto
     * @return
     */
    @ApiOperation(value = "查询所有分享信息", notes = "查询所有分享信息")
    @PostMapping(value = "/list")
    public ResponseResult getShareInfoList(@RequestBody PageDto pageDto, @RequestHeader(value = "X-Token", required = false) String token) {
        System.out.println(token.length());
        Integer userId = null;
        if(token.length() > 30) {
            Claims claims = this.jwtOperator.getClaimsFromToken(token);
            userId = (Integer) claims.get("id");
        } else {
            userId = 0;
        }
        System.out.println("用户id是: " + userId);
        return ResponseResult.success(shareService.getShareInfoList(pageDto, userId));
    }

    /**
     * 查询分享详情
     *
     * @param idDto
     * @return
     */
    @ApiOperation(value = "查询指定分享信息", notes = "查询指定分享信息")
    @PostMapping(value = "/detail")
    public ResponseResult getShareInfoDetailById(@RequestBody IdDto idDto) {
        return ResponseResult.success(shareService.getShareVo(idDto.getId()));
    }

    @GetMapping("test")
    //在文档中不显示，隐藏该接口
    @ApiIgnore
    public String getString() {
        return "hello";
    }

    /**
     * 查询分享详情
     *
     * @param pageDto
     * @return
     */
    @ApiOperation(value = "模糊查询分享列表信息", notes = "模糊查询分享列表信息")
    @PostMapping(value = "/keywords")
    public ResponseResult getShareInfoDetailByKeywords(@RequestBody PageDto pageDto) {
        return ResponseResult.success(shareService.getShareInfoByKeyWords(pageDto));
    }

    @ApiOperation(value = "投稿接口", notes = "投稿接口")
    @PostMapping(value = "contribute")
    public ResponseResult contributeShare(@RequestBody ShareRequestDto shareRequestDto) {
        return shareService.insertShareInfo(shareRequestDto);
    }

    @ApiOperation(value = "获取某人所有的分享列表", notes = "获取某人分享列表接口")
    @PostMapping(value = "my")
    public ResponseResult getMyShares(@RequestBody PageDto pageDto) {
        return ResponseResult.success(shareService.getShareListByUserId(pageDto));
    }

    @ApiOperation(value = "获取某人的兑换列表", notes = "获取兑换列表")
    @GetMapping(value = "/exchange/shares/{userId}")
    public ResponseResult getExchangeSharesByUserId(@PathVariable int userId) {
        return ResponseResult.success(shareService.getUserShareListsByUserId(userId));
    }

    @ApiOperation(value = "兑换下载")
    @PostMapping("/exchange/shareInfo")
    public ResponseResult exchangeShare(@RequestBody ExchangeDto exchangeDto){
        System.out.println("获取到的数据是: " + exchangeDto.toString());
        return ResponseResult.success(shareService.exchangeShare(exchangeDto.getShareId(), exchangeDto.getUserId()));
    }

    @ApiOperation(value = "获取所有未审核的帖子")
    @GetMapping(value = "/notCheck")
    public ResponseResult getNotCheckList() {
        return ResponseResult.success(shareService.getNotCheckList());
    }

}
