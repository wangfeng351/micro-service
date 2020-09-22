package com.soft1851.cloud.study.mapper;

import com.test.demo.entity.Course;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/8
 * @Version 1.0
 */
public interface CourseMapper {

    @Select("SELECT * FROM course")
    public List<Course> getList();
}
