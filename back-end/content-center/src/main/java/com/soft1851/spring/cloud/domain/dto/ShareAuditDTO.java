package com.soft1851.spring.cloud.domain.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/10/7
 * @Version 1.0
 */
@Data
@Builder
public class ShareAuditDTO {
    private String auditStatusEnum;
    private String reason;
}
