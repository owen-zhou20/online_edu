package com.sv.commonutils.ordervo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Member Vo entity for Comment
 * </p>
 *
 * @author Owen
 * @since 2022-09-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="UcenterMemberCommentVo entity ", description="Member Vo entity for Comment")
public class UcenterMemberComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Member id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "Wechat openid")
    private String openid;

    @ApiModelProperty(value = "Member mobile")
    private String mobile;

    @ApiModelProperty(value = "Member password")
    private String password;

    @ApiModelProperty(value = "Member nickname")
    private String nickname;

    @ApiModelProperty(value = "Member sex 1 female，2 male")
    private Integer sex;

    @ApiModelProperty(value = "age")
    private Integer age;

    @ApiModelProperty(value = "Member avatar")
    private String avatar;

    @ApiModelProperty(value = "Member sign")
    private String sign;

    @ApiModelProperty(value = "Forbidden Status(0:Normal,1:Forbidden)")
    private Boolean isDisabled;

    @ApiModelProperty(value = "Logic Delete 1（true）Deleted， 0（false）Not Deleted ")
    private Boolean isDeleted;

    @ApiModelProperty(value = "Create Time")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "Modified Time")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
