package com.sv.eduservice.controller;


import com.sv.commonutils.R;
import com.sv.eduservice.entity.EduCourse;
import com.sv.eduservice.entity.vo.CourseInfoVo;
import com.sv.eduservice.entity.vo.CoursePublishVo;
import com.sv.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Owen
 * @since 2022-08-11
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    // Course list TODO
    @GetMapping("courseList")
    public R getCourseList(){
        List<EduCourse> courseList = courseService.list(null);
        return R.ok().data("courseList",courseList);
    }

    // Add course
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", id);
    }

    // Get course info by course id
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo (@PathVariable String courseId){
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    // Modify course info
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updataCourseInfo(courseInfoVo);
        return R.ok();
    }

    // Get course info by course id
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo publishCourse = courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse", publishCourse);
    }

    // Modify course status as publish
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal"); // Set course status as publish
        boolean rs = courseService.updateById(eduCourse);
        if(rs == true) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    // Modify course status as draft
    @PostMapping("draftCourse/{id}")
    public R draftCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Draft"); // Set course status as draft
        boolean rs = courseService.updateById(eduCourse);
        if(rs == true) {
            return R.ok();
        }else {
            return R.error();
        }
    }

}

