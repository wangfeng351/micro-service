package com.soft1851.cloud.entity;

import com.sun.org.apache.xpath.internal.operations.Bool;
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
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    private Integer id;
    private String name;
    private Integer userId;
    private Boolean isFinish;
    private Boolean isJoin;
    private String cover;
}
