package com.sv.educenter.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Member login entity", description = "Member login entity")
public class LoginVo {
    @ApiModelProperty(value = "mobile")
    private String mobile;
    @ApiModelProperty(value = "password")
    private String password;
}
