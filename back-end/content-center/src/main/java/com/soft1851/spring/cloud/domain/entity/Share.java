package com.soft1851.spring.cloud.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/10/9
 * @Version 1.0
 */
@Table(name = "share")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("分享")
public class Share {

    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(name = "id", value = "分享id")
    private Integer id;

    @Column(name = "user_id")
    @ApiModelProperty(name = "userId", value = "用户id")
    private Integer userId;

    @Column(name = "title")
    @ApiModelProperty(name = "title", value = "标题")
    private String title;

    @Column(name = "is_original")
    @ApiModelProperty(name = "isOriginal", value = "是否原创")
    private Boolean isOriginal;

    @Column(name = "author")
    @ApiModelProperty(name = "author", value = "作者")
    private String author;

    @Column(name = "cover")
    @ApiModelProperty(name = "cover", value = "封面")
    private String cover;

    @Column(name = "summary")
    @ApiModelProperty(name = "summary", value = "摘要")
    private String summary;

    @Column(name = "price")
    @ApiModelProperty(name = "price", value = "价格")
    private Integer price;

    @Column(name = "download_url")
    @ApiModelProperty(name = "downloadUrl", value = "下载地址")
    private String downloadUrl;

    @Column(name = "buy_count")
    @ApiModelProperty(name = "buyCount", value = "购买数量")
    private Integer buyCount;

    @Column(name = "show_flag")
    @ApiModelProperty(name = "showFlag", value = "是否显示")
    private Boolean showFlag;

    @Column(name = "audit_status")
    @ApiModelProperty(name = "auditStatus", value = "状态")
    private String auditStatus;

    @Column(name = "reason")
    @ApiModelProperty(name = "reason", value = "原因")
    private String reason;

    @Column(name = "update_time")
    @ApiModelProperty(name = "updateTime", value = "更新时间")
    private Timestamp updateTime;

    @Column(name = "create_time")
    @ApiModelProperty(name = "createTime", value = "创建时间")
    private Timestamp createTime;
}
