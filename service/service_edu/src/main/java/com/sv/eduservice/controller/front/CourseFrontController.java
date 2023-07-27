package com.sv.eduservice.controller.front;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sv.commonutils.JwtUtils;
import com.sv.commonutils.R;
import com.sv.commonutils.ordervo.CourseWebVoOrder;
import com.sv.eduservice.client.OrdersClient;
import com.sv.eduservice.entity.EduCourse;
import com.sv.eduservice.entity.chapter.ChapterVo;
import com.sv.eduservice.entity.frontvo.CourseFrontVo;
import com.sv.eduservice.entity.frontvo.CourseWebVo;
import com.sv.eduservice.service.EduChapterService;
import com.sv.eduservice.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/eduservice/coursefront")
//@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrdersClient ordersClient;

    // 1. pagination select course list
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<EduCourse> pageCourse = new Page<>(page,limit);
        Map<String, Object> map = courseService.getCourseFrontList(pageCourse,courseFrontVo);
        // return pagination data
        return R.ok().data(map);
    }

    // 2. Get course info
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request){
        // Get course info by id
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);
        // Get chapters and videos by course id
        List<ChapterVo> chapterVideoList= chapterService.getChapterVideoByCourseId(courseId);

        // Get payment status for this course by course id and member id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)){
            return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList).data("isBuy",false);
        }else{
            boolean isBuy = ordersClient.isBuyCourse(courseId, memberId);
            return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList).data("isBuy",isBuy);
        }
    }

    // Get course info by course id for order
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id){
        CourseWebVo courseInfo = courseService.getBaseCourseInfo(id);
        System.out.println("courseInfo:"+courseInfo);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseInfo,courseWebVoOrder);
        System.out.println("courseWebVoOrder:"+courseWebVoOrder);
        return courseWebVoOrder;
    }



}
