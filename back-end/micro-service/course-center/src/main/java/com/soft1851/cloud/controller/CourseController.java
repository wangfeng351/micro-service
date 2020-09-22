package com.soft1851.cloud.controller;

import com.soft1851.cloud.entity.Course;
import com.soft1851.cloud.service.CourseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.xml.transform.Templates;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/16
 * @Version 1.0
 */
@RestController
@RequestMapping("course")
public class CourseController {
    @Resource
    private CourseService courseService;
    @Resource
    private RestTemplate restTemplate;
}
