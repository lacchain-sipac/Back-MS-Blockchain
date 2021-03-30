package com.invest.honduras.listener;

import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.everis.blockchain.honduras.util.ConstantsRevertReason;
import com.invest.honduras.blockchain.DigitalIdentityBlockChain;
import com.invest.honduras.config.ApplicationBlockChain;
import com.invest.honduras.dao.QueueDao;
import com.invest.honduras.dao.UserDao;
import com.invest.honduras.domain.model.QueueTransaction;
import com.invest.honduras.domain.model.QueueTransactionFail;
import com.invest.honduras.domain.model.UserQueue;
import com.invest.honduras.domain.model.UserRoleProjectQueue;
import com.invest.honduras.domain.model.UserRoleQueue;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ComponentQueueAsync {

	@Async
	public void startQueue(ApplicationBlockChain app, QueueDao queueDao, UserDao userDao,
			DigitalIdentityBlockChain identityBlockchain) {

		log.info("---------ComponentQueue.startQueue --------- ");
		ComponentQueue.start = true;

		while (true) {
			log.info("ComponentQueue.in while");
			List<QueueTransaction> list = queueDao.findAll();

			if (list != null && !list.isEmpty()) {

				for (QueueTransaction tx : list) {

					queueDao.deleteTransaction(tx.getId());

					log.info("attempts: {}, total: {}", tx.getAttempts(), app.getProp().getAttempsQueue());

					String error = null;
					for (int attempts = 0; attempts < app.getProp().getAttempsQueue(); attempts++) {

						if (tx.getUser() != null) {
							error = identityBlockchain.addUserBlockChain(tx.getUser(), userDao, queueDao, app);
							if (error == null)
								break;
						}

						if (tx.getUserRole() != null && !tx.getUserRole().isInsert()) {
							error = identityBlockchain.removeRoleUser(tx.getUserRole().getProxyAddressAdministrator(),
									tx.getUserRole().getProxyUser(), tx.getUserRole().getRole(), queueDao, app);
							if (error == null)
								break;
						}

						if (tx.getUserRole() != null && tx.getUserRole().isInsert()) {
							error = identityBlockchain.addRoleUser(tx.getUserRole().getProxyAddressAdministrator(),
									tx.getUserRole().getProxyUser(), tx.getUserRole().getRole(), queueDao, app);
							if (error == null)
								break;
						}

						if (tx.getUserRoleProject() != null) {

							error = identityBlockchain.setRoleUserProjectIM(tx.getUserRoleProject().getRole(),
									tx.getUserRoleProject().getUser(), tx.getUserRoleProject().getProjectCodeHash(),
									tx.getUserRoleProject().getProxyAddressUserSession(), queueDao, app);
							if (error == null)
								break;

						}
					}

					if (error != null) {
						insertFail(queueDao, tx, error);
					}

				}

				list.clear();
				list = null;
			} else {
				break;
			}

		}

		ComponentQueue.start = false;

		log.info("+++++ComponentQueue.FinishQueue +++ ");

	}

	private void insertFail(QueueDao queueDao, QueueTransaction tx, String error) {
		if (tx.getUser() != null) {
			insertErrorQueueUser(queueDao, tx.getUser().getProxyAddressAdministrator(), tx.getUser().getEmailUser(),
					error);
		}

		if (tx.getUserRole() != null && !tx.getUserRole().isInsert()) {
			insertErrorQueueUserRole(queueDao, tx.getUserRole().getProxyAddressAdministrator(),
					tx.getUserRole().getProxyUser(), tx.getUserRole().getRole(), false, error);
		}

		if (tx.getUserRole() != null && tx.getUserRole().isInsert()) {
			insertErrorQueueUserRole(queueDao, tx.getUserRole().getProxyAddressAdministrator(),
					tx.getUserRole().getProxyUser(), tx.getUserRole().getRole(), true, error);
		}

		if (tx.getUserRoleProject() != null) {

			insertErrorQueueUserRoleProject(tx.getUserRoleProject().getRole(), tx.getUserRoleProject().getUser(),
					tx.getUserRoleProject().getProjectCodeHash(), tx.getUserRoleProject().getProxyAddressUserSession(),
					queueDao, error);

		}

	}

	private void insertErrorQueueUser(QueueDao queueDao, String proxyAdministrator, String emailUser, String message) {

		UserQueue userQueu = new UserQueue();
		userQueu.setEmailUser(emailUser);
		userQueu.setProxyAddressAdministrator(proxyAdministrator);

		QueueTransactionFail tx = new QueueTransactionFail();
		tx.setUser(userQueu);
		// tx.setAttempts(attempts);

		queueDao.addTransactionFail(tx);
	}

	private void insertErrorQueueUserRole(QueueDao queueDao, String proxyAdministrator, String proxyUser, String role,
			boolean insert, String revertReason) {

		UserRoleQueue userQueu = new UserRoleQueue();
		userQueu.setProxyUser(proxyUser);
		userQueu.setProxyAddressAdministrator(proxyAdministrator);
		userQueu.setRole(role);
		userQueu.setInsert(insert);

		if (!StringUtils.isEmpty(revertReason)
				&& revertReason.contains(ConstantsRevertReason.IDENTITY_NOT_PRIVILEGES)) {
			QueueTransactionFail tx = new QueueTransactionFail();
			tx.setUserRole(userQueu);
			tx.setRevertReason(revertReason);
			queueDao.addTransactionFail(tx);

		} else {

			QueueTransactionFail tx = new QueueTransactionFail();
			tx.setUserRole(userQueu);
			// tx.setAttempts(attempts);
			queueDao.addTransactionFail(tx);
		}
	}

	private void insertErrorQueueUserRoleProject(String role, String user, String projectCodeHash,
			String proxyAddressUserSession, QueueDao queueDao, String revertReason) {

		UserRoleProjectQueue userProject = new UserRoleProjectQueue();
		userProject.setProjectCodeHash(projectCodeHash);
		userProject.setProxyAddressUserSession(proxyAddressUserSession);
		userProject.setRole(role);
		userProject.setUser(user);

		if (!StringUtils.isEmpty(revertReason)
				&& revertReason.contains(ConstantsRevertReason.IDENTITY_NOT_PRIVILEGES)) {
			QueueTransactionFail tx = new QueueTransactionFail();
			tx.setUserRoleProject(userProject);
			tx.setRevertReason(revertReason);
			queueDao.addTransactionFail(tx);
		} else {
			QueueTransactionFail tx = new QueueTransactionFail();
			tx.setUserRoleProject(userProject);
			// tx.setAttempts(attempts);
			queueDao.addTransactionFail(tx);
		}
	}

}
