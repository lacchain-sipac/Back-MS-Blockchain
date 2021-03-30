package com.invest.honduras.service;

import com.invest.honduras.request.RequestHasRoleUser;
import com.invest.honduras.request.RequestHasRoleUserProject;
import com.invest.honduras.request.RequestUpdateUser;
import com.invest.honduras.request.RequestUser;
import com.invest.honduras.request.RequestUserCap;
import com.invest.honduras.request.RequestUserRoleProject;
import com.invest.honduras.response.ResponseGeneral;
import com.invest.honduras.response.ResponseGeneralBool;

import reactor.core.publisher.Mono;

public interface DigitalIdentityService {
	Mono<ResponseGeneral> addUser(RequestUser request);

	Mono<ResponseGeneral> updateUser(RequestUpdateUser request);

	Mono<ResponseGeneral> setCap(RequestUserCap request);

	Mono<ResponseGeneralBool> checkCap(RequestUserCap request);
	 
	Mono<ResponseGeneral> setRoleUserProject(RequestUserRoleProject request);

	Mono<ResponseGeneral> removeRoleUserProject(RequestUserRoleProject request);
	
	 Mono<ResponseGeneralBool> hasRoleUser(RequestHasRoleUser request) ;
	 
	 Mono<ResponseGeneralBool> hasRoleUserProject(RequestHasRoleUserProject request);

}
