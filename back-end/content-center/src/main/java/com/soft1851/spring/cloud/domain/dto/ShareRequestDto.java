package com.soft1851.spring.cloud.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/10/7
 * @Version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShareRequestDto {
    private Integer userId;
    private String author;
    private String downloadUrl;
    private Boolean isOriginal;
    private Integer price;
    private String summary;
    private String title;
}
