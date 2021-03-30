package com.invest.honduras.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestUserRoleProject {
	private String role;
	private String user;
	private String projectCodeHash;
	private String proxyAddressUserSession;
}
