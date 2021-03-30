package com.invest.honduras.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.invest.honduras.dao.UserDao;
import com.invest.honduras.domain.model.User;
import com.invest.honduras.repository.UserRepository;

import reactor.core.publisher.Mono;

@Component
public class UserDaoImpl implements UserDao {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private ReactiveMongoTemplate mongoTemplate;

	@Override
	public void updateUser(String email, String proxyAddress) {

		Query query = new Query(Criteria.where("email").is(email));
		Update update = new Update().set("proxyAddress", proxyAddress);
		mongoTemplate.upsert(query, update, "user").subscribe(); // return's old person object

	}

	@Override
	public Mono<User> findByEmail(String email) {
		Query query = new Query(Criteria.where("email").is(email));

		return mongoTemplate.findOne(query, User.class, "user");

		// userRepository.findByEmail(email);

	}

}
