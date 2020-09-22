package com.soft1851.cloud.service.impl;

import com.soft1851.cloud.entity.Course;
import com.soft1851.cloud.entity.UserDto;
import com.soft1851.cloud.mapper.CourseMapper;
import com.soft1851.cloud.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/16
 * @Version 1.0
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Resource
    private CourseMapper courseMapper;
    @Resource
    private RestTemplate restTemplate;

    @Override
    public List<Map<String, Object>> getUserCourseList() {
        List<Map<String, Object>> userCourses = new ArrayList<>();

        List<Course> courses = courseMapper.getCourseList();
        for(int i = 0; i < courses.size(); i++) {
            int id = courses.get(i).getUserId();
            UserDto user = restTemplate.getForObject("http://172.16.75.99:8010/user/info/{id}", UserDto.class, id);
            System.out.println("获取到的用户信息是>>>>>>" + user);
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("userInfo", user);
            map.put("courseInfo", courses.get(i));
            userCourses.add(map);
        }
        return userCourses;
    }
}
