package com.invest.honduras.response;

import com.invest.honduras.util.HttpResponse;

public class ResponseGeneralProject extends HttpResponse<ProjectItem>{

	public ResponseGeneralProject(int status, ProjectItem data) {
		super(status, data);
	}
	public ResponseGeneralProject(int status,  String error) {
		super(status, null, error);
	}
}