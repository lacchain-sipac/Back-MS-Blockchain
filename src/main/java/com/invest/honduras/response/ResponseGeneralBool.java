package com.invest.honduras.response;

import com.invest.honduras.util.HttpResponse;

public class ResponseGeneralBool extends HttpResponse<ResponseBoolItem>{

	public ResponseGeneralBool(int status, ResponseBoolItem data) {
		super(status, data);
	}

	public ResponseGeneralBool(int status, String  error) {
		super(status, null, error);
	}
}