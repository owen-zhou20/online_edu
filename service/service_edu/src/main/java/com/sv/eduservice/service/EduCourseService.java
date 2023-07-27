package com.sv.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sv.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sv.eduservice.entity.frontvo.CourseFrontVo;
import com.sv.eduservice.entity.frontvo.CourseWebVo;
import com.sv.eduservice.entity.vo.CourseInfoVo;
import com.sv.eduservice.entity.vo.CoursePublishVo;
import com.sv.eduservice.entity.vo.CourseQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Course service
 * </p>
 *
 * @author Owen
 * @since 2022-08-11
 */
public interface EduCourseService extends IService<EduCourse> {

    // Add course
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    // Get course info by course id
    CourseInfoVo getCourseInfo(String courseId);

    // Update course info by courseId
    void updataCourseInfo(CourseInfoVo courseInfoVo);

    // Get course info by course id
    CoursePublishVo publishCourseInfo(String id);

    // Delete course
    boolean removeCourse(String courseId);

    // 1. pagination select course list front
    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    // Get course info by course id for order
    CourseWebVo getBaseCourseInfo(String courseId);

    // Update course view count by courseId
    void updatePageViewCount(String courseId);

    // Change publish course status. publish: flag==true, draft: flag==false
    boolean publishCourse(String id, boolean publish);

    // Pagination condition select course list
    Page<EduCourse> pageListCourse(Page<EduCourse> pageCourse, CourseQuery courseQuery);

    // Get courses for homepage
    List<EduCourse> getCourseHomepage();
}
