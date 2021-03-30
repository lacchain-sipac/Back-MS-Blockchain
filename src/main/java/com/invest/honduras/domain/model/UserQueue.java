package com.invest.honduras.domain.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserQueue  {
	private String id;
	private String emailUser;
	private String proxyAddressAdministrator;
	private List<Role> roles;

}
