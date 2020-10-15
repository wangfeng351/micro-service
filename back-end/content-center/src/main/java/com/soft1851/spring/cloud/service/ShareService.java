package com.soft1851.spring.cloud.service;

import com.soft1851.spring.cloud.common.ResponseResult;
import com.soft1851.spring.cloud.domain.dto.PageDto;
import com.soft1851.spring.cloud.domain.dto.ShareAuditDTO;
import com.soft1851.spring.cloud.domain.dto.ShareRequestDto;
import com.soft1851.spring.cloud.domain.entity.Share;
import com.soft1851.spring.cloud.domain.vo.ShareVo;

import java.util.List;


/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/24
 * @Version 1.0
 */
public interface ShareService {

    /**
     * 获取分享列表
     *
     * @return
     */
    List<Share> getShareInfoList(PageDto pageDto, int UserId);

    /**
     * 获取分享详情
     *
     * @param id
     * @return
     */
    ShareVo getShareVo(int id);

    /**
     * 通过关键词搜索
     *
     * @return
     */
    List<Share> getShareInfoByKeyWords(PageDto pageDto);

    /**
     * 投稿
     *
     * @return
     */
    ResponseResult insertShareInfo(ShareRequestDto shareRequestDto);

    /**
     * 审核分享内容信息
     *
     * @param id
     * @param shareAuditDTO
     * @return
     */
    Share audiById(Integer id, ShareAuditDTO shareAuditDTO);

    /**
     * 测试Feign调用
     *
     * @param id
     * @param shareAuditDTO
     * @return
     */
    Share audiById1(Integer id, ShareAuditDTO shareAuditDTO);

    /**
     * 测试 AsyncRestTemplate异步
     *
     * @param id
     * @param shareAuditDTO
     * @return
     */
    Share audiByAsyncRestTemplate(Integer id, ShareAuditDTO shareAuditDTO);

    /**
     * 测试 @Async异步
     *
     * @param id
     * @param shareAuditDTO
     * @return
     */
    Share audiByAsync(Integer id, ShareAuditDTO shareAuditDTO);

    /**
     * 通过用户id查询分享列表
     * @param pageDto
     * @return
     */
    List<Share> getShareListByUserId(PageDto pageDto);

    /**
     * 获取用户兑换列表
     * @param userId
     * @return
     */
    List<Share> getUserShareListsByUserId(int userId);

    /**
     * 兑换分享信息
     * @param shareId
     * @param userId
     */
    int exchangeShare(int shareId, int userId);
}
