package com.invest.honduras.config;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.everis.blockchain.honduras.IDigitalIdentity;
import com.everis.blockchain.honduras.IFlow;
import com.everis.blockchain.honduras.IRoles;
import com.everis.blockchain.honduras.IStepManager;
import com.everis.blockchain.honduras.impl.DigitalIdentityImpl;
import com.everis.blockchain.honduras.impl.FlowImpl;
import com.everis.blockchain.honduras.impl.RolesImpl;
import com.everis.blockchain.honduras.impl.StepManagerImpl;
import com.everis.blockchain.honduras.main.model.Constants;
import com.everis.blockchain.honduras.util.EthCoreParams;
import com.invest.honduras.dao.ConfigurationDao;
import com.invest.honduras.dao.QueueDao;
import com.invest.honduras.domain.model.Configuration;
import com.invest.honduras.listener.ComponentQueue;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Getter
public class ApplicationBlockChain {

	private Configuration prop;

	@Autowired
	ConfigBlockChain conf;

	@Autowired
	private ConfigurationDao dao;

	private IStepManager stepManager;

	private IDigitalIdentity iDigitalIdentity;

	private IRoles iRoles;

	private IFlow iFlow;

	@Autowired
	ComponentQueue queue;

	@Autowired
	QueueDao queueDao;

	@Bean
	public void instanceIdentityManager() throws Exception {

		prop = dao.find();

		log.info("prop.networkId :{}", prop.getNetworkId());
		log.info("prop.blockchainServer : {}" , prop.getBlockchainServer());
		log.info("prop.privateKeyBackend : {}" , conf.getPrivateKeyBackend());
		log.info("prop.contractAddressIdentityManager : {}" , prop.getContractAddressIdentityManager());
		log.info("prop.contractAddressRoles : {}" , prop.getContractAddressRoles());
		log.info("prop.contractAddressStepManager : {}" , prop.getContractAddressStepManager());
		log.info("prop.contractAddressFlow : {}" , prop.getContractAddressFlow());
		log.info("prop.didBackend : {}" , prop.getDidBackend());
		log.info("prop.AttempsQueue: {}", prop.getAttempsQueue());

		if(conf.getPrivateKeyBackend()==null){
			throw new Exception("privatekey backend is null");
		}
		EthCoreParams ethCoreParams = new EthCoreParams(prop.getBlockchainServer(), conf.getPrivateKeyBackend(), null,
				BigInteger.valueOf(0), null);

		stepManager = new StepManagerImpl(prop.getContractAddressStepManager(), ethCoreParams);

		iDigitalIdentity = new DigitalIdentityImpl(prop.getContractAddressIdentityManager(), ethCoreParams);

		iRoles = new RolesImpl(prop.getContractAddressRoles(), Constants.ROLE_ADMIN, Constants.ROLE_COO_TEC,
				ethCoreParams);

		iFlow = new FlowImpl(prop.getContractAddressFlow(), ethCoreParams);

	}

	@Scheduled(cron = "0 0/10 * * * ?")
	public void sendOEAtoBlockChain() {
		log.info("Queue 10 minuts");
		queue.startQueue();
	}

}
