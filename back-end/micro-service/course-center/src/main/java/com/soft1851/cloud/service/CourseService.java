package com.soft1851.cloud.service;

import com.soft1851.cloud.entity.Course;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/16
 * @Version 1.0
 */
@Service
public interface CourseService {

    /**
     * 获取用户及该用户的课程信息
     * @return
     */
    public List<Map<String, Object>> getUserCourseList();
}
