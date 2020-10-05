package com.soft1851.spring.cloud.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/29
 * @Version 1.0
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户信息Dto")
public class UserDto {
    @ApiModelProperty(name = "wxNickname", value = "微信昵称")
    private String wxNickname;
    @ApiModelProperty(name = "id", value = "用户id")
    private Integer id;
}
