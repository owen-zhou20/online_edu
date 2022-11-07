package com.sv.eduservice.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sv.commonutils.R;
import com.sv.eduservice.entity.EduCourse;
import com.sv.eduservice.entity.EduTeacher;
import com.sv.eduservice.service.EduCourseService;
import com.sv.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    // 1. pagination select teacher list
    @GetMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable long page, @PathVariable long limit){
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);
        Map<String,Object> map = teacherService.getTeacherFrontList(pageTeacher);
        // return pagination data
        return R.ok().data(map);
    }

    // 2. Select teacher info
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId){
        // 1. Get teacher info by teacher id
        EduTeacher eduTeacher = teacherService.getById(teacherId);
        // 2.Get course info by teacher id
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> courseList = courseService.list(wrapper);
        return R.ok().data("teacher",eduTeacher).data("courseList",courseList);


    }

}
