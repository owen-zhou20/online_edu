package com.sv.educms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * Homepage banner entity
 * </p>
 *
 * @author Owen
 * @since 2022-09-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="CrmBanner entity", description="Homepage banner entity")
public class CrmBanner implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Banner ID")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "Banner title")
    private String title;

    @ApiModelProperty(value = "Banner image url")
    private String imageUrl;

    @ApiModelProperty(value = "Banner link url")
    private String linkUrl;

    @ApiModelProperty(value = "Sort")
    private Integer sort;

    @ApiModelProperty(value = "Logic delete. 1（true）deleted， 0（false）not deleted")
    private Integer isDeleted;

    @ApiModelProperty(value = "Create time")
    private Date gmtCreate;

    @ApiModelProperty(value = "Update time")
    private Date gmtModified;


}
