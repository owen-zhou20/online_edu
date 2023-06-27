package com.sv.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sv.eduservice.entity.EduCourse;
import com.sv.eduservice.entity.EduCourseDescription;
import com.sv.eduservice.entity.frontvo.CourseFrontVo;
import com.sv.eduservice.entity.frontvo.CourseWebVo;
import com.sv.eduservice.entity.vo.CourseInfoVo;
import com.sv.eduservice.entity.vo.CoursePublishVo;
import com.sv.eduservice.entity.vo.CourseQuery;
import com.sv.eduservice.mapper.EduCourseMapper;
import com.sv.eduservice.service.EduChapterService;
import com.sv.eduservice.service.EduCourseDescriptionService;
import com.sv.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sv.eduservice.service.EduVideoService;
import com.sv.eduservice.utils.ConstantCourseUtils;
import com.sv.servicebase.exceptionhandler.SvException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * course serviceImpl
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

    // Course video and chapter service
    @Autowired
    private EduVideoService videoService;

    @Autowired
    private EduChapterService chapterService;

    // Add course
    @Override
    @Transactional
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        // 1. Add course info to edu_course table in database
        // CourseInfoVo to EduCourse
        //System.out.println("SubjectParentId ===>"+ courseInfoVo.getSubjectParentId());
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        eduCourse.setStatus(ConstantCourseUtils.COURSE_DRAFT); // Set course status as draft
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

    // Get course info by course id
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        // 1. Select edu_course table from DB
        EduCourse eduCourse = baseMapper.selectById(courseId);
        //System.out.println("course===>"+eduCourse.toString());
        if(eduCourse == null){
            throw new SvException(20001,"This course is not exist!");
        }
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);

        // 2. Select edu_course_description table from DB
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        //BeanUtils.copyProperties(courseDescription, courseInfoVo);
        if(courseDescription != null){
            courseInfoVo.setDescription(courseDescription.getDescription());
        }
        return courseInfoVo;
    }

    // Update course info by courseId
    @Override
    @Transactional
    public void updataCourseInfo(CourseInfoVo courseInfoVo) {
        System.out.println("courseInfoVo===>"+courseInfoVo.toString());
        // 1. Update edu_course table from DB
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int rs = baseMapper.updateById(eduCourse);
        if(rs == 0){
            throw new SvException(20001, "Fail to modify course info!");
        }

        // 2. Update edu_course_description table from DB
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setId(courseInfoVo.getId());
        courseDescription.setDescription(courseInfoVo.getDescription());
        boolean rsDesc = courseDescriptionService.saveOrUpdate(courseDescription);
        System.out.println("rsDesc===>"+rsDesc);
        if(rsDesc == false){
            throw new SvException(20001, "Fail to modify course description!");
        }

    }

    // Get course info by course id
    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        // Call mapper
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    // Delete course
    @Override
    @Transactional
    public boolean removeCourse(String courseId) {
        // 1. Delete videos by course id
        videoService.removeVideoByCourseId(courseId);

        // 2. Delete chapters by course id
        chapterService.removeChapterByCourseId(courseId);

        // 3. Delete course description by course id
        courseDescriptionService.removeById(courseId);

        // 4. Delete course by course id
        int rs = baseMapper.deleteById(courseId);
        if(rs == 0){
            throw new SvException(20001, "Fail to delete this course!");
        }
        return rs > 0 ;
    }

    // 1. pagination select course list front
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        // one level subject id
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){
            wrapper.eq("subject_parent_id", (courseFrontVo.getSubjectParentId()));
        }
        // two level subject id
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())){
            wrapper.eq("subject_id", (courseFrontVo.getSubjectId()));
        }
        // buy count
        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){
            if("1".equals(courseFrontVo.getBuyCountSort())){
                wrapper.orderByDesc("buy_count");
            }else{
                wrapper.orderByAsc("buy_count");
            }
        }
        // create time
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            if("1".equals(courseFrontVo.getGmtCreateSort())){
                wrapper.orderByDesc("gmt_create");
            }else{
                wrapper.orderByAsc("gmt_create");
            }
        }
        // price
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {
            if("1".equals(courseFrontVo.getPriceSort())){
                wrapper.orderByDesc("price");
            }else{
                wrapper.orderByAsc("price");
            }
        }

        baseMapper.selectPage(pageCourse,wrapper);

        // Get pagination select course data and put into map
        List<EduCourse> records = pageCourse.getRecords();
        long total = pageCourse.getTotal();
        long pages = pageCourse.getPages();
        long size = pageCourse.getSize();
        long current = pageCourse.getCurrent();

        boolean hasNext = pageCourse.hasNext();
        boolean hasPrevious = pageCourse.hasPrevious();

        Map<String,Object> map = new HashMap();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    // Get course info by id
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }

    // Change publish course status
    @Override
    public boolean publishCourse(String id, boolean publish) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        if(publish == true){
            eduCourse.setStatus(ConstantCourseUtils.COURSE_NORMAL); // Set course status as publish
        } else {
            eduCourse.setStatus(ConstantCourseUtils.COURSE_DRAFT); // Set course status as draft
        }
        int rs = baseMapper.updateById(eduCourse);
        if(rs>0){
            return true;
        }else {
            return false;
        }
    }

    // Pagination condition select course list
    @Override
    public Page<EduCourse> pageListCourse(Page<EduCourse> pageCourse, CourseQuery courseQuery) {

        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String status = courseQuery.getStatus();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();
        String gmt_create = courseQuery.getGmt_create();
        String gmt_end = courseQuery.getGmt_end();
        System.out.println("gmt_create===>"+gmt_create);
        System.out.println("gmt_end===>"+gmt_end);

        QueryWrapper<EduCourse> wrapper = new QueryWrapper();

        if(!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(teacherId)){
            wrapper.eq("teacher_id",teacherId);
        }
        if(!StringUtils.isEmpty(status)){
            wrapper.eq("status",status);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            wrapper.eq("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            wrapper.eq("subject_id", subjectId);
        }
        if(!StringUtils.isEmpty(gmt_create)){
            wrapper.ge("gmt_create",gmt_create);
        }
        if(!StringUtils.isEmpty(gmt_end)){
            wrapper.le("gmt_create",gmt_end);
        }

        //sort by create time
        wrapper.orderByDesc("gmt_create");

        baseMapper.selectPage(pageCourse, wrapper);
        return pageCourse;
    }
}
