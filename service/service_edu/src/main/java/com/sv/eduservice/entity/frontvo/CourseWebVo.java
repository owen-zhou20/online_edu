package com.sv.eduservice.entity.frontvo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel(value="Course info for each course", description="course describe in front web")
@Data
public class CourseWebVo {

    private String id;

    @ApiModelProperty(value = "course title")
    private String title;

    @ApiModelProperty(value = "price, 0 is free")
    private BigDecimal price;

    @ApiModelProperty(value = "course lessonNum")
    private Integer lessonNum;

    @ApiModelProperty(value = "course cover")
    private String cover;

    @ApiModelProperty(value = "buyCount")
    private Long buyCount;

    @ApiModelProperty(value = "viewCount")
    private Long viewCount;

    @ApiModelProperty(value = "course desc")
    private String description;

    @ApiModelProperty(value = "teacher ID")
    private String teacherId;

    @ApiModelProperty(value = "teacher name")
    private String teacherName;

    @ApiModelProperty(value = "teacher intro in one sentence")
    private String intro;

    @ApiModelProperty(value = "teacher avatar")
    private String avatar;

    @ApiModelProperty(value = "one subject ID")
    private String subjectLevelOneId;

    @ApiModelProperty(value = "one subject title")
    private String subjectLevelOne;

    @ApiModelProperty(value = "two subject ID")
    private String subjectLevelTwoId;

    @ApiModelProperty(value = "two subject title")
    private String subjectLevelTwo;

}
