package com.invest.honduras.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestHasRoleUserProject {
	private String role;
	private String userProxyAddress;
	private String projectCodeHash;
}
