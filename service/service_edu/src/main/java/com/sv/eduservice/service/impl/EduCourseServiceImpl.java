package com.sv.eduservice.service.impl;

import com.sv.eduservice.entity.EduCourse;
import com.sv.eduservice.entity.EduCourseDescription;
import com.sv.eduservice.entity.vo.CourseInfoVo;
import com.sv.eduservice.mapper.EduCourseMapper;
import com.sv.eduservice.service.EduCourseDescriptionService;
import com.sv.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sv.servicebase.exceptionhandler.SvException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Owen
 * @since 2022-08-11
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    // Course desc service
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    // Add course
    @Transactional
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        // 1. Add course info to edu_course table in database
        // CourseInfoVo to EduCourse
        //System.out.println("SubjectParentId ===>"+ courseInfoVo.getSubjectParentId());
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if(insert == 0){
            // add failed
            throw new SvException(20001,"Failed to add new course info");
        }

        // Get eduCourse id
        String cid = eduCourse.getId();

        // 2. Add course desc to edu_course_description table in database
        EduCourseDescription courseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, courseDescription);
        // Set id in courseDescription as eduCourse id
        courseDescription.setId(cid);
        courseDescriptionService.save(courseDescription);

        return cid;
    }
}
