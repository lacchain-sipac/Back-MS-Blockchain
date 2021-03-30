package com.invest.honduras.blockchain;

import com.invest.honduras.request.RequestDocumentStep;
import com.invest.honduras.request.RequestStep;
import com.invest.honduras.response.ResponseGeneral;
import com.invest.honduras.response.ResponseGeneralDocument;
import com.invest.honduras.response.ResponseGeneralProject;

public interface StepManagerBlockchain {
	ResponseGeneral initProject(RequestStep request);

	ResponseGeneral changeStep(RequestStep request, String stepPrevius);

	ResponseGeneral setDocument(RequestDocumentStep request);

	ResponseGeneralProject getProject(String hashProject);

	ResponseGeneralDocument getDocument(String hashProject, String documentType, String document);

	ResponseGeneralDocument getComment(String hashProject, String documentType, String comment);
}
