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
 * acl_user_role Table
 * </p>
 *
 * @author
 * @since
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("acl_user_role")
@ApiModel(value="UserRole entity", description="acl_user_role table entity")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "PrimaryKey Id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "Role Id")
    private String roleId;

    @ApiModelProperty(value = "User Id")
    private String userId;

    @ApiModelProperty(value = "Logical Delete 1（true）Deleted， 0（false）Not Deleted")
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "Create Time")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "Modified Time")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
