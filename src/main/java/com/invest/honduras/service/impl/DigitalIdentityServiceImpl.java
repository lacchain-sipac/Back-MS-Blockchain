package com.invest.honduras.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.invest.honduras.blockchain.DigitalIdentityBlockChain;
import com.invest.honduras.config.ApplicationBlockChain;
import com.invest.honduras.dao.QueueDao;
import com.invest.honduras.dao.UserDao;
import com.invest.honduras.domain.model.QueueTransaction;
import com.invest.honduras.domain.model.Role;
import com.invest.honduras.domain.model.User;
import com.invest.honduras.domain.model.UserQueue;
import com.invest.honduras.domain.model.UserRoleProjectQueue;
import com.invest.honduras.domain.model.UserRoleQueue;
import com.invest.honduras.request.RequestHasRoleUser;
import com.invest.honduras.request.RequestHasRoleUserProject;
import com.invest.honduras.request.RequestUpdateUser;
import com.invest.honduras.request.RequestUser;
import com.invest.honduras.request.RequestUserCap;
import com.invest.honduras.request.RequestUserRoleProject;
import com.invest.honduras.response.ResponseGeneral;
import com.invest.honduras.response.ResponseGeneralBool;
import com.invest.honduras.response.ResponseItem;
import com.invest.honduras.service.DigitalIdentityService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class DigitalIdentityServiceImpl implements DigitalIdentityService {

	@Autowired
	DigitalIdentityBlockChain identityBlockchain;

	@Autowired
	UserDao userDao;

	@Autowired
	QueueDao queueDao;

	@Autowired
	ApplicationBlockChain app;

	private void insertQueueUser(String proxyAdministrator, String emailUser, List<Role> roles) {
		//log.info("insertQueueUser {},{},{}: ", proxyAdministrator, emailUser, new Gson().toJson(roles));
		UserQueue userQueu = new UserQueue();
		userQueu.setEmailUser(emailUser);
		userQueu.setProxyAddressAdministrator(proxyAdministrator);
		userQueu.setRoles(roles);

		QueueTransaction tx = new QueueTransaction();
		tx.setUser(userQueu);

		queueDao.addTransaction(tx);
	}

	private void insertQueueUserRoleProject(String role, String user, String projectCodeHash,
			String proxyAddressUserSession, QueueDao queueDao) {

		UserRoleProjectQueue userProject = new UserRoleProjectQueue();
		userProject.setProjectCodeHash(projectCodeHash);
		userProject.setProxyAddressUserSession(proxyAddressUserSession);
		userProject.setRole(role);
		userProject.setUser(user);

		QueueTransaction tx = new QueueTransaction();
		tx.setUserRoleProject(userProject);

		queueDao.addTransaction(tx);

	}

	@Override
	public Mono<ResponseGeneral> addUser(RequestUser request) {
		return userDao.findByEmail(request.getEmailUser()).map(user -> {

			insertQueueUser(request.getProxyAddressAdministrator(), request.getEmailUser(), user.getRoles());

			return new ResponseGeneral(HttpStatus.OK.value(), new ResponseItem("ok"));

		});
	}

	@Override
	public Mono<ResponseGeneral> updateUser(RequestUpdateUser request) {
		return userDao.findByEmail(request.getEmailUser()).map(user -> {

			log.info("updateUser exists? user:{} ", new Gson().toJson(user));

			if (StringUtils.isEmpty(user.getProxyAddress())) {

				insertQueueUser(request.getProxyAddressAdministrator(), request.getEmailUser(), user.getRoles());

			} else {
				//user.setRolesOld(request.getOldRole());

				updateUserBlockChain(request.getProxyAddressAdministrator(), user, request.getOldRole());

			}

			return new ResponseGeneral(HttpStatus.OK.value(), new ResponseItem("ok"));

		});
	}

	@Override
	public Mono<ResponseGeneral> setCap(RequestUserCap request) {

		return identityBlockchain.setCap(request.getProxyAddressUser(), request.getAddressDevice(), app);

	}

	@Override
	public Mono<ResponseGeneralBool> checkCap(RequestUserCap request) {

		return identityBlockchain.checkCap(request.getProxyAddressUser(), request.getAddressDevice(), app);

	}

	@Override
	public Mono<ResponseGeneralBool> hasRoleUser(RequestHasRoleUser request) {

		return identityBlockchain.hasRoleUser(request.getRole(), request.getUserProxyAddress(), app);

	}

	@Override
	public Mono<ResponseGeneralBool> hasRoleUserProject(RequestHasRoleUserProject request) {
		return identityBlockchain.hasRoleUserProject(request.getRole(), request.getUserProxyAddress(),
				request.getProjectCodeHash(), app);

	}

	@Override
	public Mono<ResponseGeneral> setRoleUserProject(RequestUserRoleProject request) {

		insertQueueUserRoleProject(request.getRole(), request.getUser(), request.getProjectCodeHash(),
				request.getProxyAddressUserSession(), queueDao);

		return Mono.just(new ResponseGeneral(HttpStatus.OK.value(), new ResponseItem("ok")));

	}

	@Override
	public Mono<ResponseGeneral> removeRoleUserProject(RequestUserRoleProject request) {

		identityBlockchain.removeRoleUserProjectIM(request.getRole(), request.getUser(), request.getProjectCodeHash(),
				request.getProxyAddressUserSession(), queueDao, app);

		return Mono.just(new ResponseGeneral(HttpStatus.OK.value(), new ResponseItem("ok")));

	}

	public void updateUserBlockChain(String proxyAdministrator, User userItem, List<Role> listRoleOld) {

//		List<Role> listRoleOld = userItem.getRolesOld();
//		
//		ListUtil.removeSimilarRole(listRoleOld, listRoleNew);
//
//		if (listRoleOld != null) {
//			listRoleOld.forEach(role -> {
//
//				insertErrorQueueUserRole( proxyAdministrator, userItem.getProxyAddress(), role.getCode(), false);
//
//			});
//		}

//		if (listRoleNew != null) { 
		log.info("updateUserBlockChain.proxyAdministrator {}, {}", proxyAdministrator, new Gson().toJson(userItem.getRoles()));

		for (Role role : userItem.getRoles()) {
			log.info("user:{}, role:{}", userItem.getProxyAddress(), role.getCode());
			insertErrorQueueUserRole(proxyAdministrator, userItem.getProxyAddress(), role.getCode(), true);

		}
//		}

	}

	private void insertErrorQueueUserRole(String proxyAdministrator, String proxyUser, String role, boolean insert) {

		UserRoleQueue userQueu = new UserRoleQueue();
		userQueu.setProxyUser(proxyUser);
		userQueu.setProxyAddressAdministrator(proxyAdministrator);
		userQueu.setRole(role);
		userQueu.setInsert(insert);

		QueueTransaction tx = new QueueTransaction();
		tx.setUserRole(userQueu);

		queueDao.addTransaction(tx);
	}

}
