package com.sv.eduservice.controller;


import com.sv.commonutils.R;
import com.sv.eduservice.entity.vo.CourseInfoVo;
import com.sv.eduservice.entity.vo.CoursePublishVo;
import com.sv.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        CoursePublishVo coursePublish= courseService.publishCourseInfo(id);
        return R.ok().data("coursePublish", coursePublish);
    }

}

