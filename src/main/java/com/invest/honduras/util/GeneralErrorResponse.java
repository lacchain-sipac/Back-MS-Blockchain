package com.invest.honduras.util;

public class GeneralErrorResponse extends HttpResponse<String> {

	public GeneralErrorResponse(int status, String error) {
		super(status, null, error);
	}

}
