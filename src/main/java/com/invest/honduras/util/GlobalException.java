package com.invest.honduras.util;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GlobalException extends RuntimeException {

	private static final long serialVersionUID = 2329765667676467946L;

	public GlobalException(HttpStatus status, String message) {
		super(message);
		this.setHttpStatus(status);
	}

	public GlobalException(HttpStatus status, String message, Throwable e) {
		super(message, e);
		this.setHttpStatus(status);
	}

	private HttpStatus httpStatus;
}