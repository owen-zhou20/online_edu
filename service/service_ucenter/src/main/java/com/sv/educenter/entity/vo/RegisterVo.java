package com.sv.educenter.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="Member register entity", description="Member register entity")
public class RegisterVo {
    @ApiModelProperty(value = "nickname")
    private String nickname;

    @ApiModelProperty(value = "mobile")
    private String mobile;

    @ApiModelProperty(value = "password")
    private String password;

    @ApiModelProperty(value = "code")
    private String code;

}
