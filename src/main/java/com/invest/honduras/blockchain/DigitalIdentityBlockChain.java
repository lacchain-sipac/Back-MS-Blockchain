package com.invest.honduras.blockchain;

import com.invest.honduras.config.ApplicationBlockChain;
import com.invest.honduras.dao.QueueDao;
import com.invest.honduras.dao.UserDao;
import com.invest.honduras.domain.model.UserQueue;
import com.invest.honduras.response.ResponseGeneral;
import com.invest.honduras.response.ResponseGeneralBool;

import reactor.core.publisher.Mono;

public interface DigitalIdentityBlockChain {

	String addUserBlockChain(UserQueue userRole, UserDao userDao, QueueDao queueDao, ApplicationBlockChain app);

	Mono<ResponseGeneral> setCap(String proxyAddressUser, String addressDevice, ApplicationBlockChain app);

	Mono<ResponseGeneralBool> checkCap(String proxyAddressUser, String addressDevice, ApplicationBlockChain app);

	String removeRoleUserProjectIM(String role, String user, String projectCodeHash, String proxyAddressUserSession,
			QueueDao queueDao, ApplicationBlockChain app);

	Mono<ResponseGeneralBool> hasRoleUserProject(String role, String userProxyAddress, String projectCodeHash,
			ApplicationBlockChain app);

	Mono<ResponseGeneralBool> hasRoleUser(String role, String userProxyAddress, ApplicationBlockChain app);

	String addRoleUser(String proxyAdministrator, String proxyAddress, String role, QueueDao queue,
			ApplicationBlockChain app);

	String removeRoleUser(String proxyAdministrator, String proxyAddress, String role, QueueDao queue,
			ApplicationBlockChain app);

	String setRoleUserProjectIM(String role, String user, String projectCodeHash, String proxyAddressUserSession,
			QueueDao queueDao, ApplicationBlockChain app);

}
