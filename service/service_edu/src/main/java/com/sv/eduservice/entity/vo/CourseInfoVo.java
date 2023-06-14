package com.sv.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@ApiModel(value = "Course info vo", description = "Course info vo for course controller")
@Data
public class CourseInfoVo {
    @ApiModelProperty(value = "Course ID")
    private String id;

    @ApiModelProperty(value = "Course teacher ID")
    private String teacherId;

    @ApiModelProperty(value = "Course subject ID")
    private String subjectId;

    @ApiModelProperty(value = "Course parent subject ID")
    //@Value("1557256647536111617")
    private String subjectParentId;

    @ApiModelProperty(value = "Course title")
    private String title;

    // Price 0.01
    @ApiModelProperty(value = "Course price, 0 is free")
    private BigDecimal price;

    @ApiModelProperty(value = "Total course lessons No.")
    private Integer lessonNum;

    @ApiModelProperty(value = "Course cover path")
    private String cover;

    @ApiModelProperty(value = "Course description")
    private String description;


}
