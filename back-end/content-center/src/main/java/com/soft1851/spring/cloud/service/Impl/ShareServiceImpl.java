package com.soft1851.spring.cloud.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.soft1851.spring.cloud.common.AuditStatusEnum;
import com.soft1851.spring.cloud.common.ResponseResult;
import com.soft1851.spring.cloud.common.ResultCode;
import com.soft1851.spring.cloud.domain.dto.*;
import com.soft1851.spring.cloud.domain.entity.MidUserShare;
import com.soft1851.spring.cloud.domain.entity.Share;
import com.soft1851.spring.cloud.domain.vo.ShareVo;
import com.soft1851.spring.cloud.feignclient.UserCenterFeignClient;
import com.soft1851.spring.cloud.mapper.MidUserShareMapper;
import com.soft1851.spring.cloud.mapper.ShareMapper;
import com.soft1851.spring.cloud.service.ShareService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/24
 * @Version 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareServiceImpl implements ShareService {
    private final ShareMapper shareMapper;
    private final RestTemplate restTemplate;
    private final UserCenterFeignClient userCenterFeignClient;
    private final RocketMQTemplate rocketMQTemplate;
    private final AsyncRestTemplate asyncRestTemplate;
    private final MidUserShareMapper midUserShareMapper;

    @Override
    public List<Share> getShareInfoList(PageDto pageDto) {
        Example example = new Example(Share.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("showFlag", true);
        PageHelper.startPage(pageDto.getPageIndex(), pageDto.getPageSize(), "create_time desc");
        List<Share> shareList = shareMapper.selectByExample(example);
        System.out.println(shareList);
        //分页查询
        PageInfo<Share> pageInfo = new PageInfo<>(shareList);
        return pageInfo.getList();
    }

    @Override
    public ShareVo getShareVo(int id) {
        ShareVo shareVo = new ShareVo();
        Share share = shareMapper.selectByPrimaryKey(id);
        UserDto userDto = restTemplate.getForObject("http://47.111.64.183:8006/user/{id}", UserDto.class, id);
        /*UserDto userDto = userCenterFeignClient.getUserDtoById(share.getUserId());*/
        BeanUtils.copyProperties(share, shareVo);
        shareVo.setWxNickname(userDto.getWxNickname());
        return shareVo;
    }

    @Override
    public List<Share> getShareInfoByKeyWords(PageDto pageDto) {
        //启动分页
        PageHelper.startPage(pageDto.getPageIndex(), pageDto.getPageSize());
        //构造查询实例
        Example example = new Example(Share.class);
        Example.Criteria criteria = example.createCriteria();
        //如果标题不为空，再加上模糊查询条件，否则结果返回所有数据
        if (!StringUtil.isEmpty(pageDto.getKeywords())) {
            System.out.println("进入模糊查询条件赋值");
            criteria.andLike("title", "%" + pageDto.getKeywords() + "%");
        }
        System.out.println("获取的keywords是： " + pageDto.getKeywords());
        List<Share> shares = shareMapper.selectByExample(example);
        System.out.println("获取到的数据是: " + shares);
        PageInfo<Share> pageInfo = new PageInfo<>(shares);
        List<Share> shareDel;
        return pageInfo.getList();
    }

    @Override
    public ResponseResult insertShareInfo(ShareRequestDto shareRequestDto) {
        Share share = Share.builder().author(shareRequestDto.getAuthor())
                .showFlag(false).buyCount(0).cover("http://www.baidu.com")
                .isOriginal(shareRequestDto.getIsOriginal())
                .price(shareRequestDto.getPrice())
                .summary(shareRequestDto.getSummary())
                .title(shareRequestDto.getTitle())
                .createTime(Timestamp.valueOf(LocalDateTime.now()))
                .updateTime(Timestamp.valueOf(LocalDateTime.now()))
                .reason("")
                .userId(shareRequestDto.getUserId())
                .auditStatus("NOT_YET")
                .downloadUrl(shareRequestDto.getDownloadUrl())
                .build();
        int n = shareMapper.insert(share);
        if (n == 0) {
            System.out.println("投稿异常");
            return ResponseResult.failure(ResultCode.DATABASE_ERROR, "投稿异常");
        } else {
            System.out.println("投稿成功");
            return ResponseResult.success();
        }
    }

    @Override
    public Share audiById(Integer id, ShareAuditDTO shareAuditDTO) {
        //1. 查询share是否存在，不存在或者当前的audit_status != NOT_YET,那么抛异常
        long start = System.currentTimeMillis();
        Share share = this.shareMapper.selectByPrimaryKey(id);
        if (share == null) {
            throw new IllegalArgumentException("参数非法! 该分享不存在");
        }
        if (!Objects.equals("NOT_YET", share.getAuditStatus())) {
            throw new IllegalArgumentException("参数非法! 该分享已审核通过或审核不通过！");
        }
        //2. 审核资源,将状态改为PASS或REJECT
        //这个API的主要流程是审核，所以不需要等更新积分的结果返回，可以将加积分改为异步
        share.setAuditStatus(shareAuditDTO.getAuditStatusEnum().toString());
        this.shareMapper.updateByPrimaryKey(share);
        //3. 如果是PASS，那么发送消息给rocketmq，让用户中心去消费，并为发布人添加积分
        if ("PASS".equals(shareAuditDTO.getAuditStatusEnum())) {
            System.out.println("进入发消息方法：");
            this.rocketMQTemplate.convertAndSend(
                    "hello-bonus",
                    UserAddBonusMsgDTO.builder()
                            .userId(share.getUserId())
                            .bounds(100)
                            .build()
            );
        }
        System.out.println("消耗的时间是： " + (System.currentTimeMillis() - start));
        return share;
    }

    @Override
    public Share audiById1(Integer id, ShareAuditDTO shareAuditDTO) {
        //1. 查询share是否存在，不存在或者当前的audit_status != NOT_YET,那么抛异常
        long start = System.currentTimeMillis();
        Share share = this.shareMapper.selectByPrimaryKey(id);
        if (share == null) {
            throw new IllegalArgumentException("参数非法! 该分享不存在");
        }
        if (!Objects.equals("NOT_YET", share.getAuditStatus())) {
            throw new IllegalArgumentException("参数非法! 该分享已审核通过或审核不通过！");
        }
        //2. 审核资源,将状态改为PASS或REJECT
        //这个API的主要流程是审核，所以不需要等更新积分的结果返回，可以将加积分改为异步
        share.setAuditStatus(shareAuditDTO.getAuditStatusEnum().toString());
        this.shareMapper.updateByPrimaryKey(share);
        System.out.println("用户id是>>>>" + share.getUserId());
        System.out.println("是否通过: " + AuditStatusEnum.PASS.equals(shareAuditDTO.getAuditStatusEnum()));
        //3. 如果是PASS，那么发送消息给rocketmq，让用户中心去消费，并为发布人添加积分
        if ("PASS".equals(shareAuditDTO.getAuditStatusEnum())) {
            System.out.println("进入发消息方法：");
            //feign的调用
            userCenterFeignClient.updateUserBonusByUserId(UserAddBonusMsgDTO.builder().userId(share.getUserId()).bounds(100).build());
        }
        System.out.println("消耗的时间是： " + (System.currentTimeMillis() - start) + "ms");
        return share;
    }

    @Override
    public Share audiByAsyncRestTemplate(Integer id, ShareAuditDTO shareAuditDTO) {
        String url = "http://localhost:8006/user/update/bonus1";
        //设置Header
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        //1. 查询share是否存在，不存在或者当前的audit_status != NOT_YET,那么抛异常
        long start = System.currentTimeMillis();
        Share share = this.shareMapper.selectByPrimaryKey(id);
        if (share == null) {
            throw new IllegalArgumentException("参数非法! 该分享不存在");
        }
        if (!Objects.equals("NOT_YET", share.getAuditStatus())) {
            throw new IllegalArgumentException("参数非法! 该分享已审核通过或审核不通过！");
        }
        //2. 审核资源,将状态改为PASS或REJECT
        //这个API的主要流程是审核，所以不需要等更新积分的结果返回，可以将加积分改为异步
        share.setAuditStatus(shareAuditDTO.getAuditStatusEnum().toString());
        this.shareMapper.updateByPrimaryKey(share);
        //3. 如果是PASS，那么发送消息给rocketmq，让用户中心去消费，并为发布人添加积分
        if ("PASS".equals(shareAuditDTO.getAuditStatusEnum())) {
            System.out.println("AsyncRestTemplate异步发送消息：");
            UserAddBonusMsgDTO userAddBonusMsgDTO = UserAddBonusMsgDTO.builder().userId(share.getUserId()).bounds(100).build();
            HttpEntity<Object> httpEntity = new HttpEntity<>(userAddBonusMsgDTO, headers);
            //异步发送
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(userAddBonusMsgDTO);
            ListenableFuture<ResponseEntity<Integer>> addBounds = asyncRestTemplate.postForEntity(url, httpEntity, Integer.class);
            addBounds.addCallback(result -> log.info(result.getBody().toString()), (e) -> log.error(e.getMessage()));
        }
        System.out.println("消耗的时间是： " + (System.currentTimeMillis() - start) + "ms");
        return share;
    }

    @Override
    public Share audiByAsync(Integer id, ShareAuditDTO shareAuditDTO) {
        String url = "http://localhost:8006/user/update/bonus";
        //1. 查询share是否存在，不存在或者当前的audit_status != NOT_YET,那么抛异常
        Share share = this.shareMapper.selectByPrimaryKey(id);
        if (share == null) {
            throw new IllegalArgumentException("参数非法! 该分享不存在");
        }
        if (!Objects.equals("NOT_YET", share.getAuditStatus())) {
            throw new IllegalArgumentException("参数非法! 该分享已审核通过或审核不通过！");
        }
        //2. 审核资源,将状态改为PASS或REJECT
        //这个API的主要流程是审核，所以不需要等更新积分的结果返回，可以将加积分改为异步
        share.setAuditStatus(shareAuditDTO.getAuditStatusEnum().toString());
        this.shareMapper.updateByPrimaryKey(share);
        System.out.println("现在的时间是i: " + LocalDateTime.now());
        //3. 如果是PASS，那么发送消息给rocketmq，让用户中心去消费，并为发布人添加积分
        if ("PASS".equals(shareAuditDTO.getAuditStatusEnum())) {
            System.out.println("AsyncRestTemplate异步发送消息：");
            auditByAsync1(share.getUserId());
        }
        return share;
    }

    @Override
    public List<Share> getShareListByUserId(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPageIndex(), pageDto.getPageSize());
        Example example = new Example(Share.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", pageDto.getUserId());
        return new PageInfo<>(shareMapper.selectByExample(example)).getList();
    }

    @Override
    public List<Share> getUserShareListsByUserId(int userId) {
        Example example = new Example(MidUserShare.class);
        Example.Criteria userCriteria = example.createCriteria();
        userCriteria.andEqualTo("userId", userId);
        List<MidUserShare> userShares = midUserShareMapper.selectByExample(example);
        List<Share> shares = new ArrayList<>();
        System.out.println("用户兑换的分享：" + userShares.size());
//        userShares.stream().map(e -> {
//            System.out.println("分享id是: " + e);
//           Share share = shareMapper.selectByPrimaryKey(e.getShareId());
//           shares.add(share);
//            System.out.println(share);
//            System.out.println("分享列表： " + shares);
//           return shares;
//        });
        userShares.forEach(e -> {
            Share share = shareMapper.selectByPrimaryKey(e.getShareId());
            shares.add(share);
        });
        return shares;
    }

    @Async
    public void auditByAsync1(int userId) {
        System.out.println("现在的时间是： " + LocalDateTime.now());
        userCenterFeignClient.updateUserBonusByUserId(UserAddBonusMsgDTO.builder().userId(userId).bounds(100).build());
    }
}
