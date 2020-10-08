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
 * @Date 2020/10/7
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "bonus_event_log")
public class BonusEventLog {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "value")
    private Integer value;
    @Column(name = "event")
    private String event;
    @Column(name = "create_time")
    private Timestamp createTime;
    @Column(name = "description")
    private String description;
}
