package com.invest.honduras.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invest.honduras.blockchain.StepManagerBlockchain;
import com.invest.honduras.repository.RuleRepository;
import com.invest.honduras.request.RequestDocumentStep;
import com.invest.honduras.request.RequestStep;
import com.invest.honduras.response.ResponseGeneral;
import com.invest.honduras.response.ResponseGeneralDocument;
import com.invest.honduras.response.ResponseGeneralProject;
import com.invest.honduras.service.StepManagerService;
import com.invest.honduras.util.Constants;
import com.invest.honduras.util.FlowUtil;

import reactor.core.publisher.Mono;

@Service
public class StepManagerServiceImpl implements StepManagerService {

	@Autowired
	StepManagerBlockchain stepBlockchain;

	@Autowired
	RuleRepository ruleDao;

	@Override
	public  Mono<ResponseGeneral> initProject(RequestStep request) {

		return Mono.just(stepBlockchain.initProject(request));

	}
	
	@Override
	public Mono<ResponseGeneralProject> getProject(String hashProject) {
		return Mono.just(stepBlockchain.getProject(hashProject));
	}
	
	@Override
	public Mono<ResponseGeneralDocument> getComment(String hashProject, String documentType,
			String comment) {
		return Mono.just(stepBlockchain.getComment(hashProject, documentType, comment));
	}
	
	
	@Override
	public Mono<ResponseGeneralDocument> getDocument(String hashProject, String documentType,
			String document) {
		return Mono.just(stepBlockchain.getComment(hashProject, documentType, document));
	}

	@Override
	public  Mono<ResponseGeneral> addDocumentStep(RequestDocumentStep request) {

		return Mono.just(stepBlockchain.setDocument(request));

	}

	@Override
	public Mono<ResponseGeneral> changeStep(RequestStep request) {
		return ruleDao.findByCode(Constants.CODE_PROJECT).map(rule -> {
			return stepBlockchain.changeStep(request, FlowUtil.getStepPrevius(rule, request.getStep()));
		});
	}


}
