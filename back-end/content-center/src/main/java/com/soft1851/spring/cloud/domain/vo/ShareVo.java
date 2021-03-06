package com.soft1851.spring.cloud.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/29
 * @Version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShareVo {
    private Integer id;
    private String author;
    private String auditStatus;
    private Integer buyCount;
    private String cover;
    private Timestamp createTime;
    private String downloadUrl;
    private Boolean isOriginal;
    private Integer price;
    private String reason;
    private Boolean showFlag;
    private String summary;
    private String title;
    private String type;
    private Timestamp updateTime;
    private String wxNickname;
}
