package com.sv.eduservice.service;

import com.sv.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sv.eduservice.entity.vo.CourseInfoVo;
import com.sv.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 服务类
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

    // Modify course info
    void updataCourseInfo(CourseInfoVo courseInfoVo);

    // Get course info by course id
    CoursePublishVo publishCourseInfo(String id);
}
