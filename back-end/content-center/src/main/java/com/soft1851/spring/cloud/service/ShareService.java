package com.soft1851.spring.cloud.service;

import com.soft1851.spring.cloud.domain.dto.PageDto;
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
     * @return
     */
    List<Share> getShareInfoList(PageDto pageDto);

    /**
     * 获取分享详情
     * @param id
     * @return
     */
    ShareVo getShareVo(int id);

    /**
     * 通过关键词搜索
     * @return
     */
    List<Share> getShareInfoByKeyWords(PageDto pageDto);
}
