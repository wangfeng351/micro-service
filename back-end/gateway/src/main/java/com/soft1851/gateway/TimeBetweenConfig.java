package com.soft1851.gateway;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @Description 定义开始和结束的两个参数
 * @Author wf
 * @Date 2020/10/9
 * @Version 1.0
 */
@Data
public class TimeBetweenConfig {
    private LocalTime start;
    private LocalTime end;
}
