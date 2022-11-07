package com.sv.eduservice.mapper;

import com.sv.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sv.eduservice.entity.frontvo.CourseWebVo;
import com.sv.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author Owen
 * @since 2022-08-11
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    public CoursePublishVo getPublishCourseInfo(String courseId);

    // Get course info by id
    CourseWebVo getBaseCourseInfo(String courseId);
}
