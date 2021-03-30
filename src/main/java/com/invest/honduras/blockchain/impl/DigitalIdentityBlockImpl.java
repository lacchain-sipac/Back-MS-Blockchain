package com.invest.honduras.blockchain.impl;

import java.math.BigInteger;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.everis.blockchain.honduras.main.model.Constants;
import com.everis.blockchain.honduras.util.Utils;
import com.invest.honduras.blockchain.DigitalIdentityBlockChain;
import com.invest.honduras.config.ApplicationBlockChain;
import com.invest.honduras.dao.QueueDao;
import com.invest.honduras.dao.UserDao;
import com.invest.honduras.domain.model.Role;
import com.invest.honduras.domain.model.UserQueue;
import com.invest.honduras.response.ResponseBoolItem;
import com.invest.honduras.response.ResponseGeneral;
import com.invest.honduras.response.ResponseGeneralBool;
import com.invest.honduras.response.ResponseItem;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class DigitalIdentityBlockImpl implements DigitalIdentityBlockChain {

	@Override
	public String addUserBlockChain(UserQueue userqueue, UserDao userDao, QueueDao queue, ApplicationBlockChain app) {

		try {

			if (!app.getIRoles().hasRoleUser(Constants.ROLE_ADMIN, userqueue.getProxyAddressAdministrator())) {
				log.info("Error.addRoleUser: NOT_HAVE_ROLE_1", userqueue.getProxyAddressAdministrator());
				return null;
			}

			String proxyAddressUser = app.getIDigitalIdentity().createIdentity("", "", "", userqueue.getEmailUser(),
					"");

			log.info("addUserBlockChain {}, proxy:{}", userqueue.getEmailUser(), proxyAddressUser);

			userDao.updateUser(userqueue.getEmailUser(), proxyAddressUser);
			String error = null;
			if (userqueue.getRoles() != null) {
				for (Role rol : userqueue.getRoles()) {

					error = addRoleUser(userqueue.getProxyAddressAdministrator(), proxyAddressUser, rol.getCode(),
							queue, app);
				}
			}
			return error;
		} catch (Exception e) {

			String message = Utils.getRevertReason(e);

			log.error("Error.addUserBlockChain {}", message);

			return message;

		}

	}

	@Override
	public String addRoleUser(String proxyAdministrator, String proxyAddress, String role, QueueDao queue,
			ApplicationBlockChain app) {

		try {

			if (!app.getIRoles().hasRoleUser(Constants.ROLE_ADMIN, proxyAdministrator)) {
				log.info("Error.addRoleUser: NOT_HAVE_ROLE_1", proxyAdministrator);
				return null;
			}

			boolean flag = app.getIRoles().hasRoleUser(role, proxyAddress);

			log.info("hasRoleUser. role {},user{}, HASH:{}", role, proxyAddress, flag);

			if (!flag) {

				log.info("addRoleUser role:{}, user:{}, admin:{}, im:{}", role, proxyAddress, proxyAdministrator,
						app.getProp().getContractAddressIdentityManager());

				String hash = app.getIRoles().setRoleUserIM(role, proxyAddress, proxyAdministrator,
						app.getProp().getContractAddressIdentityManager());

				log.info(" OK ROLE {}, {}, {} ", proxyAddress, role, hash);
			}
			return null;
		} catch (Exception e) {
			String message = Utils.getRevertReason(e);
			log.error("Error.DigitalIdentityBlockImpl.addRole {}", message);
			
			return message;
			//insertErrorQueueUserRole(queue, proxyAdministrator, proxyAddress, role, true, message);

		}
	}

	@Override
	public String removeRoleUser(String proxyAdministrator, String proxyAddress, String role, QueueDao queue,
			ApplicationBlockChain app) {
		try {
			if (!app.getIRoles().hasRoleUser(Constants.ROLE_ADMIN, proxyAdministrator)) {
				log.info("Error.addRoleUser: NOT_HAVE_ROLE_1", proxyAdministrator);
				return null;
			}

			if (app.getIRoles().hasRoleUser(role, proxyAddress)) {

				String hash = app.getIRoles().removeRoleUserIM(role, proxyAddress, proxyAdministrator,
						app.getProp().getContractAddressIdentityManager());

				log.info(" remove ROLE = " + role + " - " + hash);
			}
			return null;
		} catch (Exception e) {

			String message = Utils.getRevertReason(e);

			log.error("Error.DigitalIdentityBlockImpl.removeRole {}", message);

			return message;
			//insertErrorQueueUserRole(queue, proxyAdministrator, proxyAddress, role, false, message);

		}
	}

	@Override
	public Mono<ResponseGeneral> setCap(String proxyAddress, String addressDevice, ApplicationBlockChain app) {

		try {

			addressDevice = addressDevice.substring(2);

			addressDevice = "0x" + addressDevice.toUpperCase();

			String cap = "fw";
			Date date = new Date();
			long time = date.getTime() / 1000;

			log.info("setCap proxyAddress {}, addressDeviceKay: {} ", proxyAddress, addressDevice);

			String tx = app.getIDigitalIdentity().setCap(proxyAddress, addressDevice, cap, BigInteger.valueOf(time),
					BigInteger.valueOf(0));

			log.info("setCap OK proxyAddress {}, addressDeviceKay: {} ", proxyAddress, addressDevice);

			return Mono.just(new ResponseGeneral(HttpStatus.OK.value(), new ResponseItem(tx)));

		} catch (Exception e) {
			
			log.error("Error.setCap proxyAddress {}, addressDeviceKay: {}, ERROR: {} ", proxyAddress, addressDevice,
					Utils.getRevertReason(e));

			return Mono.just(new ResponseGeneral(HttpStatus.BAD_REQUEST.value(), Utils.getRevertReason(e)));
		}

	}

	@Override
	public Mono<ResponseGeneralBool> checkCap(String proxy, String addressDeviceKay, ApplicationBlockChain app) {
		try {
			String addressDevice = addressDeviceKay.substring(2);

			addressDevice = "0x" + addressDevice.toUpperCase();

			String cap = "fw";
			log.info("proxyAddressUser : " + proxy);
			log.info("addressDevice : " + addressDevice);

			boolean flag = app.getIDigitalIdentity().checkCap(proxy, addressDevice, cap);

			log.info("checkCap proxyAddress {}, addressDeviceKay: {}, flag:{} ", proxy, addressDevice, flag);

			return Mono.just(new ResponseGeneralBool(HttpStatus.OK.value(), new ResponseBoolItem(flag)));

		} catch (Exception e) {
			log.error("Error.checkCap {}", Utils.getRevertReason(e));
			return Mono.just(new ResponseGeneralBool(HttpStatus.BAD_REQUEST.value(), Utils.getRevertReason(e)));
		}

	}

	@Override
	public Mono<ResponseGeneralBool> hasRoleUserProject(String role, String user, String projectCodeHash,
			ApplicationBlockChain app) {

		try {

			boolean flag = app.getIRoles().hasRoleUser(role, user, projectCodeHash);

			log.info("hasRoleUserProject. role {},user{}, projectCodeHash{}, HASH:{}", role, user, projectCodeHash,
					flag);

			return Mono.just(new ResponseGeneralBool(HttpStatus.OK.value(), new ResponseBoolItem(flag)));

		} catch (Exception e) {
			log.error("Error.hasRoleUserProject {}", Utils.getRevertReason(e));

			return Mono.just(new ResponseGeneralBool(HttpStatus.BAD_REQUEST.value(), Utils.getRevertReason(e)));
		}

	}

	@Override
	public Mono<ResponseGeneralBool> hasRoleUser(String role, String userProxyAddress, ApplicationBlockChain app) {

		try {
			boolean flag = app.getIRoles().hasRoleUser(role, userProxyAddress);

			log.info("hasRoleUser. role {},user{}, HASH:{}", role, userProxyAddress, flag);

			return Mono.just(new ResponseGeneralBool(HttpStatus.OK.value(), new ResponseBoolItem(flag)));

		} catch (Exception e) {
			log.error("Error.hasRoleUser {}", Utils.getRevertReason(e));
			return Mono.just(new ResponseGeneralBool(HttpStatus.BAD_REQUEST.value(), Utils.getRevertReason(e)));
		}

	}

	@Override
	public String setRoleUserProjectIM(String role, String user, String projectCodeHash, String proxyAddressUserSession,
			QueueDao queueDao, ApplicationBlockChain app) {

		try {
			
			
			boolean flagRoleAdminProject = app.getIRoles().hasRoleUser(Constants.ROLE_COO_TEC, user);

			log.info("has flagRoleAdminProject ROLE_COO_TEC :{}", flagRoleAdminProject);

			if (flagRoleAdminProject) {
				boolean flag = app.getIRoles().hasRoleUser(role, user, projectCodeHash);

				log.info("hasRoleUser. role {},user{}, HASH:{}", role, user, flag);

				if (!flag) {

					log.info("setRoleUserProjectIM :" + role + "," + user + "," + projectCodeHash + ","
							+ proxyAddressUserSession + "," + app.getProp().getContractAddressIdentityManager());

					String tx = app.getIRoles().setRoleUserIM(role, user, projectCodeHash, proxyAddressUserSession,
							app.getProp().getContractAddressIdentityManager());

					log.info("setRoleUserProjectIM.SUCCESS : " + tx);
				}
			}

			return null;
		} catch (Exception e) {

			String message = Utils.getRevertReason(e);

			log.error("Error.setRoleUserProjectIM {}", message);
			
			return message;
	//		insertErrorQueueUserRoleProject(role, user, projectCodeHash, proxyAddressUserSession, queueDao, message);

		}
	}



	@Override
	public String removeRoleUserProjectIM(String role, String user, String projectCodeHash,
			String proxyAddressUserSession, QueueDao queueDao, ApplicationBlockChain app) {

		try {
			boolean flagRoleAdminProject = app.getIRoles().hasRoleUser(Constants.ROLE_COO_TEC, user);

			log.info("has flagRoleAdminProject ROLE_COO_TEC :{}", flagRoleAdminProject);

			if (flagRoleAdminProject) {

				if (app.getIRoles().hasRoleUser(role, user, projectCodeHash)) {
					String tx = app.getIRoles().removeRoleUserIM(role, user, projectCodeHash, proxyAddressUserSession,
							app.getProp().getContractAddressIdentityManager());
					log.info("removeRoleUserIM " + tx);
				}
			}

			return null;
		} catch (Exception e) {
			String message = Utils.getRevertReason(e);

			log.error("Error.removeRoleUserProjectIM {}", message);
			
			return message;
		}

	}

}
