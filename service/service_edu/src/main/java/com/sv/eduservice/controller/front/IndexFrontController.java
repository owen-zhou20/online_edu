package com.sv.eduservice.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sv.commonutils.R;
import com.sv.eduservice.entity.EduCourse;
import com.sv.eduservice.entity.EduTeacher;
import com.sv.eduservice.service.EduCourseService;
import com.sv.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
@Cacheable(key = "'selectCourseTeacherList'",value = "CourseTeacherList")
//@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    // Select hottest 8 courses and hottest 4 teachers
    @GetMapping("index")
    public R index(){

        // Get courses for homepage
        List<EduCourse> courseList = courseService.getCourseHomepage();

        // Get teachers for homepage
        List<EduTeacher> teacherList = teacherService.getTeacherHomepage();

        return R.ok().data("courseList",courseList).data("teacherList",teacherList);
    }

}
