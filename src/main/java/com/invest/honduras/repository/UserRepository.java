package com.invest.honduras.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.invest.honduras.domain.model.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {


	@Query(value = "{'roles.code': ?0 }")
	Flux<User> findUserByRol(String code);

	Mono<User> findByEmail(String email);
	
	//@Query("{'email': ?0 } , { $set: {'proxyAddress': ?1}}")
 //   Mono<User> updateProxyAddress(String email, String proxyAddress);

	
	
}
