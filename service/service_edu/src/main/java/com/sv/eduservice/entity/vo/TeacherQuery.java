package com.sv.eduservice.entity.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

// teacher vo for condition select teachers
@Data
public class TeacherQuery {


    @ApiModelProperty(value = "teacher name")
    private String name;

    @ApiModelProperty(value = "teacher level  1:Normal Teacher, 2:Head Teacher")
    private Integer level;

    @ApiModelProperty(value = "create time", example = "10:10:10 01-01-2019")
    private String begin;//String type from front-end

    @ApiModelProperty(value = "modify time", example = "10:10:10 01-01-2019")
    private String end;
}
