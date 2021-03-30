package com.invest.honduras.response;

import com.invest.honduras.util.HttpResponse;

public class ResponseGeneral extends HttpResponse<ResponseItem>{

	public ResponseGeneral(int status, ResponseItem data) {
		super(status, data);
	}
	
	public ResponseGeneral(int status, String error) {
		super(status, null, error);
	}

}