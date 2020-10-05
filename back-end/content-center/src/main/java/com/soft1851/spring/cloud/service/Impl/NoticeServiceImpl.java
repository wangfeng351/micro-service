package com.soft1851.spring.cloud.service.Impl;

import com.soft1851.spring.cloud.domain.entity.Notice;
import com.soft1851.spring.cloud.mapper.NoticeMapper;
import com.soft1851.spring.cloud.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/10/4
 * @Version 1.0
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NoticeServiceImpl implements NoticeService {
    private final NoticeMapper noticeMapper;

    @Override
    public Notice getLastNotice() {
        Example example = new Example(Notice.class);
        //设置降序
        example.setOrderByClause("create_time DESC");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("showFlag", 1);
        return noticeMapper.selectByExample(example).get(0);
    }
}
