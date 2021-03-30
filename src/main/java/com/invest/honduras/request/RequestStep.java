package com.invest.honduras.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestStep {
	@ApiModelProperty(notes = "email de usuario" , example = "mail@email.com")
	private String projectCode;
	private String proxyAddress;
	private String role;
	private String step;
}
