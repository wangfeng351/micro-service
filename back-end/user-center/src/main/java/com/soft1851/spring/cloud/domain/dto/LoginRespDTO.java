package com.soft1851.spring.cloud.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/10/13
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRespDTO {
    /**
     * 用户信息
     */
    private UserRespDTO user;
    /**
     * token数据
     */
    private JwtTokenRespDTO token;

    /**
     * 是否签到
     */
    private boolean isSignIn;
}
