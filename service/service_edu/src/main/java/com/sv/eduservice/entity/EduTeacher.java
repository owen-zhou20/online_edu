package com.sv.eduservice.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * EduTeacher entity
 * </p>
 *
 * @author Owen
 * @since 2022-06-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="EduTeacher entity", description="EduTeacher entity")
public class EduTeacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Teacher ID")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "Teacher Name")
    private String name;

    @ApiModelProperty(value = "Teacher introduction")
    private String intro;

    @ApiModelProperty(value = "Teacher career")
    private String career;

    @ApiModelProperty(value = "Teacher level, 1: Normal Teacher 2: Head Teacher")
    private Integer level;

    @ApiModelProperty(value = "Avatar")
    private String avatar;

    @ApiModelProperty(value = "Sort")
    private Integer sort;

    @ApiModelProperty(value = "Logical delete 1（true）Deleted， 0（false）Not deleted")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty(value = "Create time")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "Update time")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
