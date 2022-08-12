package com.sv.eduservice.service;

import com.sv.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sv.eduservice.entity.vo.CourseInfoVo;

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
    void saveCourseInfo(CourseInfoVo courseInfoVo);
}
