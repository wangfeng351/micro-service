package com.soft1851.cloud.mapper;

import com.soft1851.cloud.entity.Course;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/16
 * @Version 1.0
 */
public interface CourseMapper {

    /**
     * 获取所有课程信息
     * @return
     */
    @Select("SELECT * FROM course")
    public List<Course> getCourseList();
}
