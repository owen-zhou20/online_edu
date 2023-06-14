package com.sv.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CourseQuery {

    @ApiModelProperty(value = "Course title")
    private String title;

    @ApiModelProperty(value = "Course publish status")
    private String status;

    @ApiModelProperty(value = "Select create time", example = "2019-01-01 10:10:10")
    private String gmt_create;//String

}
