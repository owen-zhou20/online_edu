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
 * PayLog entity
 * </p>
 *
 * @author Owen
 * @since 2022-10-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_pay_log")
@ApiModel(value="PayLog entity", description="PayLog entity for member")
public class PayLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "Id")
    private String id;

    @ApiModelProperty(value = "Order Number")
    private String orderNo;

    @ApiModelProperty(value = "Payment time")
    private Date payTime;

    @ApiModelProperty(value = "Total Payment Fee(AUD)")
    private BigDecimal totalFee;

    @ApiModelProperty(value = "Transaction Id")
    private String transactionId;

    @ApiModelProperty(value = "Trade State")
    private String tradeState;

    @ApiModelProperty(value = "Pay Type（1：WeChat 2：Alipay）")
    private Integer payType;

    @ApiModelProperty(value = "Other Attributes")
    private String attr;

    @ApiModelProperty(value = "Logic Delete. 1（true）deleted， 0（false）not deleted")
    private Integer isDeleted;

    @ApiModelProperty(value = "Create Time")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "Update Time")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
