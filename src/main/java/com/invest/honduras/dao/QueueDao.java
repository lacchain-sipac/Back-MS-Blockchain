package com.invest.honduras.dao;

import java.util.List;

import com.invest.honduras.domain.model.QueueTransaction;
import com.invest.honduras.domain.model.QueueTransactionFail;

public interface QueueDao {
	List<QueueTransaction> findAll();

	void addTransaction(QueueTransaction tx);

	void deleteTransaction(String id);

	void addTransactionFail(QueueTransactionFail tx);

	void updateCountTransaction(String id, int count);

	void migrationFailToQueue();
}
