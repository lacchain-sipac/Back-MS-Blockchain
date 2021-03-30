package com.invest.honduras.response;

import com.invest.honduras.util.HttpResponse;

public class ResponseGeneralDocument extends HttpResponse<DocumentItem>{

	public ResponseGeneralDocument(int status, DocumentItem data) {
		super(status, data);
	}

	public ResponseGeneralDocument(int status,  String error) {
		super(status,null, error);
	}
}