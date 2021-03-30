package com.invest.honduras.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.invest.honduras.dao.QueueDao;
import com.invest.honduras.domain.model.QueueTransaction;
import com.invest.honduras.domain.model.QueueTransactionFail;
import com.invest.honduras.listener.ComponentQueue;
import com.invest.honduras.repository.QueueFailTxRepository;
import com.invest.honduras.repository.QueueTxRepository;

@Component
public class QueueDaoImpl implements QueueDao {
	public static final String TX_NAME_COLLECTION = "queue_tx";
	public static final String TX_NAME_COLLECTION_FAIL = "queue_tx_fail";

	@Autowired
	ComponentQueue componentQueue;

	@Autowired
	QueueTxRepository queue;

	@Autowired
	QueueFailTxRepository repoFail;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<QueueTransaction> findAll() {
		return queue.findAll();
	}

	@Override
	public void addTransaction(QueueTransaction tx) {

		mongoTemplate.insert(tx, TX_NAME_COLLECTION); // return's old person object

		componentQueue.startQueue();
	}

	@Override
	public void addTransactionFail(QueueTransactionFail tx) {

		repoFail.save(tx);
	}

	@Override
	public void deleteTransaction(String id) {
		mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), TX_NAME_COLLECTION);

	}

	@Override
	public void updateCountTransaction(String id, int count) {

		Query query = new Query(Criteria.where("_id").is(id));
		Update update = new Update().set("attempts", count);
		mongoTemplate.upsert(query, update, TX_NAME_COLLECTION);

	}

	@Override
	public void migrationFailToQueue() {
		List<QueueTransactionFail> listFail = mongoTemplate.findAll(QueueTransactionFail.class,
				TX_NAME_COLLECTION_FAIL);
		mongoTemplate.dropCollection(TX_NAME_COLLECTION_FAIL);

		mongoTemplate.insert(listFail, TX_NAME_COLLECTION);

		componentQueue.startQueue();
	}

}
