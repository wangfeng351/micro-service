package com.soft1851.spring.cloud.domain.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/10/12
 * @Version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mid_user_share")
@ApiModel("用户兑换分享信息")
public class MidUserShare {
    @Id
    private Integer id;
    private Integer userId;
    private Integer shareId;
}
