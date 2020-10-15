package com.soft1851.spring.cloud.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.soft1851.spring.cloud.common.ResponseResult;
import com.soft1851.spring.cloud.common.ResultCode;
import com.soft1851.spring.cloud.domain.dto.*;
import com.soft1851.spring.cloud.domain.entity.BonusEventLog;
import com.soft1851.spring.cloud.domain.entity.User;
import com.soft1851.spring.cloud.mapper.BonusEventLogMapper;
import com.soft1851.spring.cloud.service.UserService;
import com.soft1851.spring.cloud.util.JwtOperator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/29
 * @Version 1.0
 */
@RequestMapping(value = "user")
@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;
    private final RestTemplate restTemplate;
    private final WxMaService wxMaService;
    private final JwtOperator jwtOperator;
    private final BonusEventLogMapper bonusEventLogMapper;

    @GetMapping(value = "{id}")
    public UserDto getUserDtoById(@PathVariable int id) {
        System.out.println("获取到的数据是》》" + id);
        return userService.getUserById(id);
    }

    //可能构造的请求url: q?id=1&wxId=aaa&...
    @GetMapping("/q")
    public UserDto query(UserDto user) {
        return user;
    }

    //登录
    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginDto loginDto) {
        return ResponseResult.success(userService.login(loginDto));
    }

    //修改积分
    @PutMapping("/update/bonus")
    public ResponseResult updateBonus(@RequestBody UserAddBonusMsgDTO userAddBonusMsgDTO) {
        if (userService.updateBonus(userAddBonusMsgDTO) == 1) {
            return ResponseResult.success();
        } else {
            return ResponseResult.failure(ResultCode.DATA_IS_WRONG);
        }
    }

    //修改积分
    @PostMapping("/update/bonus1")
    public int updateBonus1(@RequestBody UserAddBonusMsgDTO userAddBonusMsgDTO) {
        return userService.updateBonus(userAddBonusMsgDTO);
    }

    @PutMapping("/update/bonus2")
    public int reduceBonus(@RequestParam int bonus, @RequestParam int userId, @RequestParam String description) {
        System.out.println("进入方法");
        return userService.reduceBonus(bonus, userId, description);
    }

    @GetMapping("/bonus/my/{userId}")
    public ResponseResult getBonusList(@PathVariable int userId) {
        return ResponseResult.success(userService.getBonusListByUserId(userId));
    }

    @PostMapping(value = "/wxLogin")
    public LoginDto codeAuth(@RequestBody WxLoginDto wxLoginDto) {
        //自己请求openId
        String appId = "wxcbc7305012661e66";
        String secret = "57a5fff6e054a1f8c88486b09dd8ed66";
        String url = "http://api.weixin.qq.com/sns/jscode?session?appid=wxcbc7305012661e66&js_code=57a5fff6e054a1f8c88486b09dd8ed66&grant_type=authorization_code";
        String formatUrl = String.format(url, appId, secret, wxLoginDto.getLoginCode());
        String result = restTemplate.getForObject(formatUrl, String.class);
        System.out.println(result);
        //通过第三方SDK获得openId
        return null;
    }

    @PostMapping(value = "/wx/login")
    public LoginRespDTO wxLogin(@RequestBody WxLoginDto wxLoginDto) throws WxErrorException {
        String openId;
        //微信小程序登录，需要根据code请求openId
        if (wxLoginDto.getLoginCode() != null) {
            //微信服务端校验是否已经登录的结果
            WxMaJscode2SessionResult result = wxMaService.getUserService()
                                            .getSessionInfo(wxLoginDto.getLoginCode());
            log.info(result.toString());
            //微信的openId, 用户在微信这边的唯一标识
            openId = result.getOpenid();
        } else {
            openId = wxLoginDto.getOpenId();
        }
        // 看用户是否注册，如果没有注册就（插入），如果已经注册就返回其信息
        User user = userService.WxLogin(wxLoginDto, openId);
        //查看今天是否签到
        Example example = new Example(BonusEventLog.class);
        Example.Criteria criteria = example.createCriteria();
        String start = DateFormatUtils.format(new Date(), "yyyy-MM-dd 00:00:00");
        String end = DateFormatUtils.format(new Date(), "yyyy-MM-dd 23:59:59");
        criteria.andEqualTo("userId", user.getId()).andBetween("createTime", Timestamp.valueOf(start), Timestamp.valueOf(end));
        BonusEventLog bonusEventLog = bonusEventLogMapper.selectOneByExample(example);
        // 颁发token
        Map<String, Object> userInfo = new HashMap<>(3);
        boolean isSignIn  = false;
        if(bonusEventLog != null) {
            isSignIn = true;
        }
        userInfo.put("id", user.getId());
        userInfo.put("wxNickname", user.getWxNickname());
        userInfo.put("role", user.getRoles());
        String token = jwtOperator.generateToken(userInfo);

        log.info("{}登录成功, 生成的token = {}, 有效期到:{}",
                user.getWxNickname(),
                token,
                jwtOperator.getExpirationTime());
        UserRespDTO userRespDTO1 = new UserRespDTO();
        BeanUtils.copyProperties(user, userRespDTO1);
        System.out.println("获取到的是否登录：" + isSignIn);
        return LoginRespDTO.builder().user(userRespDTO1)
                .token(JwtTokenRespDTO.builder().token(token).expirationTime(jwtOperator.getExpirationTime().getTime()).build())
                .isSignIn(isSignIn)
                .build();
    }

    //获取openid
    @GetMapping(value = "/test/{jsCode}")
    public Map<String, Object> getWxUserOpenid(@PathVariable String jsCode) {
        //拼接url
        StringBuilder url = new StringBuilder("http://api.weixin.qq.com/sns/jscode2session?");
        url.append("appid=");//appid设置
        url.append("wxcbc7305012661e66");
        url.append("&secret=");//secret设置
        url.append("57a5fff6e054a1f8c88486b09dd8ed66");
        url.append("&js_code=");//code设置
        url.append(jsCode);
        url.append("&grant_type=authorization_code");
        System.out.println("请求链接是: " + url);
        Map<String, Object> map = null;
        try {
            HttpClient client = HttpClientBuilder.create().build();//构建一个Client
            HttpGet get = new HttpGet(url.toString());    //构建一个GET请求
            HttpResponse response = client.execute(get);//提交GET请求
            HttpEntity result = response.getEntity();//拿到返回的HttpResponse的"实体"
            String content = EntityUtils.toString(result);
            System.out.println(content);//打印返回的信息
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @GetMapping(value = "/detail/{userId}")
    public UserDetailDto getUserInfoById(@PathVariable int userId) {
        return userService.getUserInfoById(userId);
    }

    @PutMapping(value = "/signIn/{userId}")
    public ResponseResult signIn(@PathVariable int userId) {
        return ResponseResult.success(userService.signIn(userId));
    }
}
