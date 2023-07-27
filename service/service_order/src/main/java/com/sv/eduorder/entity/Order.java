package com.sv.eduorder.entity;

import java.math.BigDecimal;

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
 * Order entity
 * </p>
 *
 * @author Owen
 * @since 2022-10-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_order")
@ApiModel(value="Order entity", description="Order entity for member")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "Id")
    private String id;

    @ApiModelProperty(value = "Order No.")
    private String orderNo;

    @ApiModelProperty(value = "Course Id")
    private String courseId;

    @ApiModelProperty(value = "Course Title")
    private String courseTitle;

    @ApiModelProperty(value = "Course cover")
    private String courseCover;

    @ApiModelProperty(value = "Teacher Id")
    private String teacherId;

    @ApiModelProperty(value = "Teacher Name")
    private String teacherName;

    @ApiModelProperty(value = "Member Id")
    private String memberId;

    @ApiModelProperty(value = "Member Nickname")
    private String nickname;

    @ApiModelProperty(value = "Member Mobile")
    private String mobile;

    @ApiModelProperty(value = "Order Total Fee(AUD)")
    private BigDecimal totalFee;

    @ApiModelProperty(value = "Pay Type(1：WeChat 2：Alipay)")
    private Integer payType;

    @ApiModelProperty(value = "Order Status(0：Not Payed 1：Payed)")
    private Integer status;

    @ApiModelProperty(value = "Logic Delete. 1（true）deleted， 0（false）not deleted")
    private Integer isDeleted;

    @ApiModelProperty(value = "Create Time")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "Update Time")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
