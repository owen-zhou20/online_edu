package com.sv.commonutils.aclvo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="userQueryVo", description="userQueryVo")
@Data
public class userQueryVo {
    @ApiModelProperty(value = "User Id")
    private String id;

    @ApiModelProperty(value = "username")
    private String username;
}
