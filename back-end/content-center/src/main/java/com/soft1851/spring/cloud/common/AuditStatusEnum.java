package com.soft1851.spring.cloud.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/10/7
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum AuditStatusEnum {
    /**
     * 待审核
     */
    NOT_YET,
    /**
     * 审核通过
     */
    PASS,
    /**
     * 审核不通过
     */
    REJECT
}
