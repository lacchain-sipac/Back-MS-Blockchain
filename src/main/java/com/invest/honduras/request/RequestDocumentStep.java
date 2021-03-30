package com.invest.honduras.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDocumentStep {
	private String projectCode;
	private String documentType;
	private String proxyAddress;
	private String documentHash;
	private String commentHash;
	private boolean finish;
	private String role;
	private String step;
}
