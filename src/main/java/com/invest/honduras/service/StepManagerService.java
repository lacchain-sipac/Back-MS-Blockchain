package com.invest.honduras.service;

import com.invest.honduras.request.RequestDocumentStep;
import com.invest.honduras.request.RequestStep;
import com.invest.honduras.response.ResponseGeneral;
import com.invest.honduras.response.ResponseGeneralDocument;
import com.invest.honduras.response.ResponseGeneralProject;

import reactor.core.publisher.Mono;

public interface StepManagerService {

	Mono<ResponseGeneral> initProject(RequestStep request);

	Mono<ResponseGeneralProject> getProject(String hashProject);

	Mono<ResponseGeneral> changeStep(RequestStep request);

	Mono<ResponseGeneral> addDocumentStep(RequestDocumentStep request);

	Mono<ResponseGeneralDocument> getDocument(String hashProject, String documentType, String document);

	Mono<ResponseGeneralDocument> getComment(String hashProject, String documentType, String comment);

}
