package com.sv.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CourseQuery {

    @ApiModelProperty(value = "Course title")
    private String title;

    @ApiModelProperty(value = "Teacher Id")
    private String teacherId;

    @ApiModelProperty(value = "Course publish status")
    private String status;

    @ApiModelProperty(value = "One subject id")
    private String subjectParentId;

    @ApiModelProperty(value = "Two subject id")
    private String subjectId;

    @ApiModelProperty(value = "Select create time", example = "2019-01-01 10:10:10")
    private String gmt_create;//String

}
