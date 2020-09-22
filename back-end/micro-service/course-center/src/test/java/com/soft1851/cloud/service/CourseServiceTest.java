package com.soft1851.cloud.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/16
 * @Version 1.0
 */
@SpringBootTest
class CourseServiceTest {
    @Resource
    private CourseService courseService;

    @Test
    void getUserCourseList() {
        System.out.println(courseService.getUserCourseList());
    }
}