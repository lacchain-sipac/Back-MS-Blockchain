package com.invest.honduras.domain.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "user")
public class User {
	private String email;
	private List<Role> roles;
	private String codeStatus;
	private String proxyAddress;
	private String did;
	//private List<Role> rolesOld;
}
