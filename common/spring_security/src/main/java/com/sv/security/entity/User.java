package com.sv.security.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * User entity
 * </p>
 *
 * @author
 * @since
 */
@Data
@ApiModel(description = "User entity")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Wechat openid")
	private String username;

	@ApiModelProperty(value = "Password")
	private String password;

	@ApiModelProperty(value = "Nickname")
	private String nickName;

	@ApiModelProperty(value = "Avatar")
	private String salt;

	@ApiModelProperty(value = "Token")
	private String token;

}



