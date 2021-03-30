package com.invest.honduras.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.invest.honduras.domain.model.QueueTransaction;

public interface QueueTxRepository extends MongoRepository<QueueTransaction, String> {
	
}
