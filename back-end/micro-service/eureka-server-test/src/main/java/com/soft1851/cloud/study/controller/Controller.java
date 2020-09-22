package com.soft1851.cloud.study.controller;

import com.soft1851.cloud.study.mapper.CourseMapper;
import com.test.demo.entity.Course;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/8
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "test")
public class Controller {
    @Resource
    private CourseMapper courseMapper;

    @GetMapping(value = "list")
    public List<Course> getList() {
        return courseMapper.getList();
    }
}
