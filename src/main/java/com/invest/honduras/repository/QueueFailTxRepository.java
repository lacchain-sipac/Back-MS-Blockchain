package com.invest.honduras.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.invest.honduras.domain.model.QueueTransactionFail;

public interface QueueFailTxRepository extends MongoRepository<QueueTransactionFail, String> {
	
}
