package com.invest.honduras.request;

import java.util.List;

import com.invest.honduras.domain.model.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestUpdateUser {
	private String proxyAddressAdministrator;
	private String emailUser;
	private List<Role> oldRole;
}
