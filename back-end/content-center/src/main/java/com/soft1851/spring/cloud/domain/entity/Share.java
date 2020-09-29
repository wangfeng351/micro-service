package com.soft1851.spring.cloud.domain.entity;

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
 * @Date 2020/9/24
 * @Version 1.0
 */
@Table(name = "share")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Share {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "title")
    private String title;

    @Column(name = "is_original")
    private Boolean isOriginal;

    @Column(name = "author")
    private String author;

    @Column(name = "cover")
    private String cover;

    @Column(name = "summary")
    private String summary;

    @Column(name = "price")
    private Integer price;

    @Column(name = "download_url")
    private String downloadUrl;

    @Column(name = "buy_count")
    private Integer buyCount;

    @Column(name = "show_flag")
    private Boolean showFlag;

    @Column(name = "audit_status")
    private String auditStatus;

    @Column(name = "reason")
    private String reason;

    @Column(name = "update_time")
    private Timestamp updateTime;

    @Column(name = "create_time")
    private Timestamp createTime;
}
