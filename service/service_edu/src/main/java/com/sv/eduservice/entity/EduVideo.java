package com.sv.eduservice.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * Course video entity
 * </p>
 *
 * @author Owen
 * @since 2022-08-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="EduVideo entity", description="Course video entity")
public class EduVideo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Course video ID")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "Course ID")
    private String courseId;

    @ApiModelProperty(value = "Chapter ID")
    private String chapterId;

    @ApiModelProperty(value = "Video title")
    private String title;

    @ApiModelProperty(value = "Video source Id in Vod Cloud")
    private String videoSourceId;

    @ApiModelProperty(value = "Video original name in Vod Cloud")
    private String videoOriginalName;

    @ApiModelProperty(value = "Sort")
    private Integer sort;

    @ApiModelProperty(value = "Play count")
    private Long playCount;

    @ApiModelProperty(value = "Course is free: 0 Not free, 1 Free")
    private Integer isFree;

    @ApiModelProperty(value = "Video duration(second)")
    private Float duration;

    @ApiModelProperty(value = "Status: Empty: not upload, Transcoding: transcoding,  Normal: normal")
    private String status;

    @ApiModelProperty(value = "Video file size(Byte)")
    private Long size;

    @ApiModelProperty(value = "Version in optimistic locking")
    private Long version;

    @ApiModelProperty(value = "Create time")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "Update time")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
