package com.sv.aclservice.entity;

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
 * acl_user Table
 * </p>
 *
 * @author Owen
 * @since
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("acl_user")
@ApiModel(value="User Entity", description="acl_user table entity")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "User Id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "Wechat Openid")
    private String username;

    @ApiModelProperty(value = "Password")
    private String password;

    @ApiModelProperty(value = "NickName")
    private String nickName;

    @ApiModelProperty(value = "Avatar")
    private String avatar;

    @ApiModelProperty(value = "Token")
    private String token;

    @ApiModelProperty(value = "Logic Delete 1（true）Deleted， 0（false）Not Deleted")
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "Create Time")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "Modified Time")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
