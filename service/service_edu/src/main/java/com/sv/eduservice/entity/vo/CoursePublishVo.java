package com.sv.eduservice.entity.vo;

import lombok.Data;

@Data
public class CoursePublishVo {

    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;// Only for presentation
    private String status;

}