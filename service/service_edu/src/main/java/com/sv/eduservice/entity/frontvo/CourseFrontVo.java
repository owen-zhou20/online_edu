package com.sv.eduservice.entity.frontvo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="Course front list vo", description="course info in front web")
@Data
public class CourseFrontVo{

    @ApiModelProperty(value = "course title")
    private String title;

    @ApiModelProperty(value = "teacher id")
    private String teacherId;

    @ApiModelProperty(value = "one level subject id")
    private String subjectParentId;

    @ApiModelProperty(value = "two level subject id")
    private String subjectId;

    @ApiModelProperty(value = "buy count")
    private String buyCountSort;

    @ApiModelProperty(value = "create time")
    private String gmtCreateSort;

    @ApiModelProperty(value = "price")
    private String priceSort;
}
