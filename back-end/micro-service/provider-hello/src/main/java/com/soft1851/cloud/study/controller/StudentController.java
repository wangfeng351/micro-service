package com.soft1851.cloud.study.controller;

import com.soft1851.cloud.study.entity.Student;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/9/13
 * @Version 1.0
 */
@Data
@RestController
@RequestMapping("/student")
public class StudentController {

    @GetMapping(value = "/one")
    public Student getOneStudent() {
        return new Student(1, "颜子皓");
    }

    @GetMapping(value = "/list")
    public List<Student> getStudentList() {
        Student[] students = new Student[]{
                new Student(1, "颜子皓"),
                new Student(2, "吴家浩"),
                new Student(3, "无言同学"),
        };
        return Arrays.asList(students);
    }
}
