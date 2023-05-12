package com.sv.aclservice.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * acl_permission Table in DB
 * Menus table
 * </p>
 *
 * @author
 * @since
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("acl_permission")
@ApiModel(value="Menu entity", description="acl_permission Table in DB. Menu entity")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Menu Id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "Parent Menu Id")
    private String pid;

    @ApiModelProperty(value = "Menu name")
    private String name;

    @ApiModelProperty(value = "Menu Type(1:Menu,2:Button)")
    private Integer type;

    @ApiModelProperty(value = "Permission Value")
    private String permissionValue;

    @ApiModelProperty(value = "Path")
    private String path;

    @ApiModelProperty(value = "Component Path")
    private String component;

    @ApiModelProperty(value = "Icon")
    private String icon;

    @ApiModelProperty(value = "Status(0:Forbidden,1:Normal)")
    private Integer status;

    @ApiModelProperty(value = "Level")
    @TableField(exist = false)
    private Integer level;

    @ApiModelProperty(value = "Children Menu List")
    @TableField(exist = false)
    private List<Permission> children;

    @ApiModelProperty(value = "IsSelect")
    @TableField(exist = false)
    private boolean isSelect;


    @ApiModelProperty(value = "Logical Delete 1（true）Deleted， 0（false）Not Delete")
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "Create Time")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "Update Time")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
