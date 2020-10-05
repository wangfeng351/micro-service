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
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import sun.security.provider.SHA;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import javax.swing.border.TitledBorder;
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
}
