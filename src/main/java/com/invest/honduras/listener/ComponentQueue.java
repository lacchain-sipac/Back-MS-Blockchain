package com.invest.honduras.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.invest.honduras.blockchain.DigitalIdentityBlockChain;
import com.invest.honduras.config.ApplicationBlockChain;
import com.invest.honduras.dao.QueueDao;
import com.invest.honduras.dao.UserDao;

@Component
public class ComponentQueue {

	@Autowired
	DigitalIdentityBlockChain identityBlockchain;

	@Autowired
	UserDao userDao;

	@Autowired
	QueueDao queueDao;

	@Autowired
	ApplicationBlockChain app;

	protected static boolean start;
	
	@Autowired
	ComponentQueueAsync async;

	public synchronized void startQueue() {

		if (!start) {

			async.startQueue(app, queueDao, userDao, identityBlockchain);
		}

	}

}
