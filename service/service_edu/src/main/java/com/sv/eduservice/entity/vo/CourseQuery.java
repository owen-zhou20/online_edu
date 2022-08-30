package com.sv.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CourseQuery {

    @ApiModelProperty(value = "Course title")
    private String title;

    @ApiModelProperty(value = "Course publish status")
    private String status;

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String gmt_create;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

}
