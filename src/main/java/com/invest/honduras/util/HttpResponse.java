package com.invest.honduras.util;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class HttpResponse<T> {

	private int status;
	private T data;
	private String error;

	// private String message;
	public HttpResponse(int status, T data) {
		this.status = status;
		this.data = data;
		// this.message = message;
	}

	public HttpResponse(int status, T data, String error) {
		this.status = status;
		this.data = data;
		this.error = error;
		// this.message = message;
	}

}
