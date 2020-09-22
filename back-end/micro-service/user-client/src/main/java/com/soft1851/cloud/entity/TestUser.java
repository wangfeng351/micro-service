package com.soft1851.cloud.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/16
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestUser {

    private Integer id;
    private String name;
    private String avatar;
}
