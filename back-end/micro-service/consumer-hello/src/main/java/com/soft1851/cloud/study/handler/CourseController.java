package com.soft1851.cloud.study.handler;

import com.soft1851.cloud.study.entity.Course;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/13
 * @Version 1.0
 */
@Data
@RestController
@RequestMapping("/consumer")
public class CourseController {
    @Resource
    private RestTemplate restTemplate;

    @GetMapping(value = "/test/list")
    public List<Course> getStudentList() {
        return restTemplate.getForObject("http://wf.com:8002/test/list", List.class);
    }
}
