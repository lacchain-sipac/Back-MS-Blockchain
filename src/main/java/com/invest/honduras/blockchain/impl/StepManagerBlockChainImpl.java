package com.invest.honduras.blockchain.impl;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.web3j.utils.Strings;

import com.everis.blockchain.honduras.Change;
import com.everis.blockchain.honduras.Project;
import com.everis.blockchain.honduras.util.Utils;
import com.google.gson.Gson;
import com.invest.honduras.blockchain.StepManagerBlockchain;
import com.invest.honduras.config.ApplicationBlockChain;
import com.invest.honduras.request.RequestDocumentStep;
import com.invest.honduras.request.RequestStep;
import com.invest.honduras.response.DocumentItem;
import com.invest.honduras.response.ProjectItem;
import com.invest.honduras.response.ResponseGeneral;
import com.invest.honduras.response.ResponseGeneralDocument;
import com.invest.honduras.response.ResponseGeneralProject;
import com.invest.honduras.response.ResponseItem;
import com.invest.honduras.util.Constants;
import com.invest.honduras.util.TypeStatusCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class StepManagerBlockChainImpl implements StepManagerBlockchain {

	@Autowired
	ApplicationBlockChain app;

	@Override
	public ResponseGeneral initProject(RequestStep request) {

		log.info(">>>initProject<<< : {}", new Gson().toJson(request));

		try {
			Project project = app.getStepManager().getProject(request.getProjectCode());
			if (Boolean.FALSE.equals(project.initialized)) {

				log.info("initProjectIM : " + request.getProjectCode() + ", " + request.getStep() + ", "
						+ app.getProp().getContractAddressFlow() + ", " + app.getProp().getContractAddressRoles() + ", "
						+ request.getRole() + ", " + request.getProxyAddress() + ", "
						+ app.getProp().getContractAddressIdentityManager());

				String tx = app.getStepManager().initProjectIM(request.getProjectCode(), request.getStep(),
						app.getProp().getContractAddressFlow(), app.getProp().getContractAddressRoles(),
						request.getRole(), request.getProxyAddress(),
						app.getProp().getContractAddressIdentityManager());

				log.info("==>SUCCESS initProjectIM " + tx);

				return new ResponseGeneral(HttpStatus.OK.value(), new ResponseItem(tx));

			} else {

				return new ResponseGeneral(HttpStatus.BAD_REQUEST.value(),
						TypeStatusCode.MESSAGE_ERROR_BLOCKCHAIN_REQUEST_ACCREDITED.name());

			}

		} catch (Exception e) {
			log.error("Error.initProjectIM " + Utils.getRevertReason(e));
			return new ResponseGeneral(HttpStatus.BAD_REQUEST.value(), Utils.getRevertReason(e));

		}
	}

	@Override
	public ResponseGeneral changeStep(RequestStep request, String stepPrevius) {

		log.info(">>>changeStep<<< {}, {}", new Gson().toJson(request), stepPrevius);

		try {
			Project project = app.getStepManager().getProject(request.getProjectCode());

			if (!app.getProp().getContractAddressFlow().equals(project.flow)) {
				return new ResponseGeneral(HttpStatus.BAD_REQUEST.value(),
						TypeStatusCode.MESSAGE_ERROR_BLOCKCHAIN_STEP_FLOW_NO_EQUALS.name());
			}

			if (Boolean.FALSE.equals(project.initialized)) {
				return new ResponseGeneral(HttpStatus.BAD_REQUEST.value(),
						TypeStatusCode.MESSAGE_ERROR_BLOCKCHAIN_PROJECT_NO_INIT.name());
			}

			if (request.getStep().equals(project.currentStep)) {

				return new ResponseGeneral(HttpStatus.OK.value(),
						"PROJECT_IS_ALREADY_IN_STEP" + ":" + project.currentStep);
			}

			if (Boolean.TRUE
					.equals(app.getStepManager().isStepCompletedProject(request.getProjectCode(), request.getStep()))) {
				return new ResponseGeneral(HttpStatus.BAD_REQUEST.value(),
						TypeStatusCode.MESSAGE_ERROR_BLOCKCHAIN_STEP_ACCREDITED.name());
			}

			if (Boolean.FALSE.equals(app.getStepManager().hasValidDocuments(request.getProjectCode()))) {
				return new ResponseGeneral(HttpStatus.BAD_REQUEST.value(),
						TypeStatusCode.MESSAGE_ERROR_BLOCKCHAIN_DOCUMENT_FINISH_ALL.name());
			}

			if (!project.currentStep.equals(stepPrevius)) {
				return new ResponseGeneral(HttpStatus.BAD_REQUEST.value(),
						TypeStatusCode.MESSAGE_ERROR_BLOCKCHAIN_STEP_PREVIUS.name());
			}

			log.info("changeStepIM : " + request.getProjectCode() + ", " + request.getStep() + ", " + request.getRole()
					+ ", " + request.getProxyAddress() + ", " + app.getProp().getContractAddressIdentityManager());

			String tx = app.getStepManager().changeStepIM(request.getProjectCode(), request.getStep(),
					request.getRole(), request.getProxyAddress(), app.getProp().getContractAddressIdentityManager());

			log.info("==> changeStepIM OK {}", tx);

			return new ResponseGeneral(HttpStatus.OK.value(), new ResponseItem(tx));

		} catch (Exception e) {
			log.error("FAIL changeStep: {}", Utils.getRevertReason(e));

			return new ResponseGeneral(HttpStatus.BAD_REQUEST.value(), Utils.getRevertReason(e));

		}
	}

	@Override
	public ResponseGeneral setDocument(RequestDocumentStep request) {

		log.info(">>>setDocument<<< {}", new Gson().toJson(request));

		try {

			if ((Constants.CODE_PHASE_03_03.equals(request.getStep())
					|| Constants.CODE_PHASE_03_04.equals(request.getStep()))
					&& (Constants.ROLE_CONT.equals(request.getRole())
							|| Constants.ROLE_SUP.equals(request.getRole()))) {

				if (Boolean.FALSE.equals(app.getIRoles().hasRoleUser(request.getRole(), request.getProxyAddress(),
						request.getProjectCode()))) {

					log.warn("user has role");

					return new ResponseGeneral(HttpStatus.BAD_REQUEST.value(),
							TypeStatusCode.MESSAGE_ERROR_BLOCKCHAIN_USER_NO_FOUND_PROJECT.name());
				}

			}

			Project project = app.getStepManager().getProject(request.getProjectCode());

			if (!project.currentStep.equals(request.getStep())) {

				log.info("changeStepIM : " + request.getProjectCode() + ", " + request.getStep() + ", "
						+ request.getRole() + ", " + request.getProxyAddress() + ", "
						+ app.getProp().getContractAddressIdentityManager());

				String tx = app.getStepManager().changeStepIM(request.getProjectCode(), request.getStep(),
						request.getRole(), request.getProxyAddress(),
						app.getProp().getContractAddressIdentityManager());

				log.info("setDocument.>> changeStepIM : " + tx);

			}

			if (!app.getProp().getContractAddressFlow().equals(project.flow)) {
				return new ResponseGeneral(HttpStatus.BAD_REQUEST.value(),
						TypeStatusCode.MESSAGE_ERROR_BLOCKCHAIN_STEP_FLOW_NO_EQUALS.name());
			}

			if (Boolean.FALSE
					.equals(app.getIFlow().existsDocumentKeyInStep(request.getStep(), request.getDocumentType()))) {
				return new ResponseGeneral(HttpStatus.BAD_REQUEST.value(),
						TypeStatusCode.MESSAGE_ERROR_BLOCKCHAIN_STEP_FILE_NO_PRESENT.name());
			}

			if (!isNotDocumentFinal(request)) {

				log.info("unfinalizeDocumentTypeIM {}, {},{},{},{}", request.getProjectCode(),
						request.getDocumentType(), request.getRole(), request.getProxyAddress(),
						app.getProp().getContractAddressIdentityManager());

				String tx = app.getStepManager().unfinalizeDocumentTypeIM(request.getProjectCode(),
						request.getDocumentType(), request.getRole(), request.getProxyAddress(),
						app.getProp().getContractAddressIdentityManager());

				log.info("unfinalizeDocumentTypeIM OK :{}", tx);

			}

			log.info("setDocumentTypeIM : " + request.getProjectCode() + "," + request.getDocumentType() + ","
					+ request.getStep() + "," + request.getRole() + "," + request.getDocumentHash() + "," + "" + ","
					+ request.getCommentHash() + "," + request.isFinish() + "," + request.getProxyAddress() + ","
					+ app.getProp().getContractAddressIdentityManager());

			String tx = app.getStepManager().setDocumentTypeIM(request.getProjectCode(), request.getDocumentType(),
					request.getStep(), request.getRole(), Utils.getHexBlank(request.getDocumentHash()),
					Utils.getHexBlank(""), Utils.getHexBlank(request.getCommentHash()), request.isFinish(),
					request.getProxyAddress(), app.getProp().getContractAddressIdentityManager());

			log.info("setDocumentTypeIM OK: {}", tx);

			return new ResponseGeneral(HttpStatus.OK.value(), new ResponseItem(tx));

		} catch (Exception e) {
			log.error("FAIL  setDocument: {}", Utils.getRevertReason(e));

			return new ResponseGeneral(HttpStatus.BAD_REQUEST.value(), Utils.getRevertReason(e));

		}
	}

	private boolean isNotDocumentFinal(RequestDocumentStep request) throws Exception {

		BigInteger countDocument = app.getStepManager().getDocumentTypeCount(request.getProjectCode(),
				request.getDocumentType());

		int count = countDocument != null ? countDocument.intValue() : -1;

		if (count > 0) {
			Change document = app.getStepManager().getDocumentType(request.getProjectCode(), request.getDocumentType(),
					new BigInteger((count - 1) + ""));

			return !document.isFinal;
		} else {
			return true;
		}
	}

	@Override
	public ResponseGeneralProject getProject(String codeProject) {

		try {
			Project project = app.getStepManager().getProject(codeProject);

			if (project != null && !project.initialized)
				return new ResponseGeneralProject(HttpStatus.OK.value(), new ProjectItem(project));
			else {
				return new ResponseGeneralProject(HttpStatus.BAD_REQUEST.value(),
						TypeStatusCode.MESSAGE_ERROR_BLOCKCHAIN_PROJECT_NOT_ACCREDITED.name());
			}
		} catch (Exception e) {
			log.error("FAIL getProject: {}", Utils.getRevertReason(e));

			return new ResponseGeneralProject(HttpStatus.BAD_REQUEST.value(), Utils.getRevertReason(e));

		}

	}

	@Override
	public ResponseGeneralDocument getComment(String codeProject, String documentType, String comment) {

		try {

			Change change = app.getStepManager().getDocumentTypeByComment(codeProject, documentType, comment);

			if (change != null && !Strings.isEmpty(change.accreditComment))
				return new ResponseGeneralDocument(HttpStatus.OK.value(),
						new DocumentItem(change, change.accreditComment));
			else {
				return new ResponseGeneralDocument(HttpStatus.BAD_REQUEST.value(),
						TypeStatusCode.MESSAGE_ERROR_BLOCKCHAIN_COMMENT_NOT_ACCREDITED.name());
			}
		} catch (Exception e) {
			log.error("FAIL getComment: {}", Utils.getRevertReason(e));

			return new ResponseGeneralDocument(HttpStatus.BAD_REQUEST.value(), Utils.getRevertReason(e));

		}
	}

	@Override
	public ResponseGeneralDocument getDocument(String codeProject, String documentType, String comment) {

		try {

			Change change = app.getStepManager().getDocumentTypeByDocument(codeProject, documentType, comment);

			if (change != null && !Strings.isEmpty(change.accreditDocument)) {
				return new ResponseGeneralDocument(HttpStatus.OK.value(),
						new DocumentItem(change, change.accreditComment));
			} else {
				return new ResponseGeneralDocument(HttpStatus.BAD_REQUEST.value(),
						TypeStatusCode.MESSAGE_ERROR_BLOCKCHAIN_DOCUMENT_NOT_ACCREDITED.name());
			}
		} catch (Exception e) {
			log.error("FAIL getDocument: {}", Utils.getRevertReason(e));

			return new ResponseGeneralDocument(HttpStatus.BAD_REQUEST.value(), Utils.getRevertReason(e));

		}

	}

}
