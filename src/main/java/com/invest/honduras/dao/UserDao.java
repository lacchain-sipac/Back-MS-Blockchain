package com.invest.honduras.dao;

import com.invest.honduras.domain.model.User;

import reactor.core.publisher.Mono;

public interface UserDao {

	void updateUser(String email, String proxyAddress);
	
	Mono<User> findByEmail(String email);	
}
