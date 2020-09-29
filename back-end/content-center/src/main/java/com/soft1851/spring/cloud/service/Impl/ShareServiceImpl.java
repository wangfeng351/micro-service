package com.soft1851.spring.cloud.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.soft1851.spring.cloud.domain.dto.PageDto;
import com.soft1851.spring.cloud.domain.dto.UserDto;
import com.soft1851.spring.cloud.domain.entity.Share;
import com.soft1851.spring.cloud.domain.vo.ShareVo;
import com.soft1851.spring.cloud.feignclient.UserCenterFeignClient;
import com.soft1851.spring.cloud.mapper.ShareMapper;
import com.soft1851.spring.cloud.service.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sun.security.provider.SHA;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/24
 * @Version 1.0
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareServiceImpl implements ShareService {
    private final ShareMapper shareMapper;
    private final RestTemplate restTemplate;
    private final UserCenterFeignClient userCenterFeignClient;

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
        //UserDto userDto = restTemplate.getForObject("http://47.111.64.183:8006/user/{id}", UserDto.class, id);
        UserDto userDto = userCenterFeignClient.getUserDtoById(id);
        /*shareVo.setAuditStatus(share.getAuditStatus());
        shareVo.setAuthor(share.getAuthor());
        shareVo.setBuyCount(share.getBuyCount());
        shareVo.setCover(share.getCover());
        shareVo.setCreateTime(share.getCreateTime());
        shareVo.setShowFlag(share.getShowFlag());
        shareVo.setDownloadUrl(share.getDownloadUrl());
        shareVo.setSummary(share.getSummary());
        shareVo.setWxNickname(userDto.getWxNickname());
        shareVo.setUpdateTime(share.getUpdateTime());
        shareVo.setId(share.getId());
        shareVo.setIsOriginal(share.getIsOriginal());
        shareVo.setReason(share.getReason());
        shareVo.setPrice(share.getPrice());
        shareVo.setTitle(share.getTitle());*/
        BeanUtils.copyProperties(share, shareVo);
        shareVo.setWxNickname(userDto.getWxNickname());
        return shareVo;
    }
}
