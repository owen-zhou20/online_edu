package com.sv.eduservice.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;

/**
 * <p>
 * Course entity
 * </p>
 *
 * @author Owen
 * @since 2022-08-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="EduCourse entity", description="Course entity")
public class EduCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Course ID")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "Course teacher ID")
    private String teacherId;

    @ApiModelProperty(value = "Course subject ID")
    private String subjectId;

    @ApiModelProperty(value = "Course parent subject ID")
    private String subjectParentId;

    @ApiModelProperty(value = "Course title")
    private String title;

    @ApiModelProperty(value = "Course price，0 is free")
    private BigDecimal price;

    @ApiModelProperty(value = "Total course lessons")
    private Integer lessonNum;

    @ApiModelProperty(value = "Course cover path")
    private String cover;

    @ApiModelProperty(value = "Buy count")
    private Long buyCount;

    @ApiModelProperty(value = "View count")
    private Long viewCount;

    @ApiModelProperty(value = "Version(Optimistic Concurrency Control)")
    private Long version;

    @ApiModelProperty(value = "Course status, Draft: not publish, Normal: published")
    private String status;

    @ApiModelProperty(value = "Logic delete. 1（true）deleted， 0（false）not deleted")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty(value = "Create time")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "Update time")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

}
