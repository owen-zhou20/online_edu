package com.sv.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CoursePublishVo {

    @ApiModelProperty(value = "Course ID")
    private String id;

    @ApiModelProperty(value = "Course title")
    private String title;

    @ApiModelProperty(value = "Course cover path")
    private String cover;

    @ApiModelProperty(value = "Total course lessons No.")
    private Integer lessonNum;

    @ApiModelProperty(value = "Course subject one")
    private String subjectLevelOne;

    @ApiModelProperty(value = "Course subject two")
    private String subjectLevelTwo;

    @ApiModelProperty(value = "Course teacher name")
    private String teacherName;

    @ApiModelProperty(value = "Course price，0 is free")
    private String price;// Only for presentation

    @ApiModelProperty(value = "Course status")
    private String status;

}
