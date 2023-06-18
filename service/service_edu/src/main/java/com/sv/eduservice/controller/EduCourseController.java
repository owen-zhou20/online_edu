package com.sv.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sv.commonutils.R;
import com.sv.eduservice.entity.EduCourse;
import com.sv.eduservice.entity.vo.CourseInfoVo;
import com.sv.eduservice.entity.vo.CoursePublishVo;
import com.sv.eduservice.entity.vo.CourseQuery;
import com.sv.eduservice.service.EduCourseService;
import com.sv.eduservice.utils.ConstantCourseUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * Course controller
 * </p>
 *
 * @author Owen
 * @since 2022-08-11
 */
@RestController
@RequestMapping("/eduservice/course")
//@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    // Course list
    // TODO wrapper
    @GetMapping("courseList")
    public R getCourseList(){
        List<EduCourse> courseList = courseService.list(null);
        return R.ok().data("courseList",courseList);
    }

    //2. pagination condition select course list
    @ApiOperation("Pagination select courses")
    @PostMapping("pageCourse/{current}/{limit}")
    public R pageListCourse(@PathVariable long current,
                             @PathVariable long limit,
                            @RequestBody(required = false) CourseQuery courseQuery) {
        Page<EduCourse> pageCourse =  new Page<>(current,limit);

        pageCourse = courseService.pageListCourse(pageCourse, courseQuery);

        long total = pageCourse.getTotal();
        List<EduCourse> courseList = pageCourse.getRecords();
        return R.ok().data("total",total).data("rows",courseList);
    }

    // Add course
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
            String courseId = courseService.saveCourseInfo(courseInfoVo);
        if(!StringUtils.isEmpty(courseId)) {
            return R.ok().data("courseId", courseId);
        }else{
            return R.error();
        }
    }

    // Get course info by course id
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo (@PathVariable String courseId){
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    // Modify course info by courseId
    @PutMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updataCourseInfo(courseInfoVo);
        return R.ok();
    }

    // Get publish course info by course id
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo publishCourse = courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse", publishCourse);
    }

    // Modify course status as publish
    @PutMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        boolean rs = courseService.publishCourse(id,true);// Set course status as publish
        if(rs == true) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    // Modify course status as draft
    @PutMapping("draftCourse/{id}")
    public R draftCourse(@PathVariable String id){
        boolean rs = courseService.publishCourse(id,false);// Set course status as draft
        if(rs == true) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    // Delete course
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        courseService.removeCourse(courseId);
        return R.ok();
    }

}

