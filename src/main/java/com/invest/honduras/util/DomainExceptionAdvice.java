package com.invest.honduras.util;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;

import lombok.RequiredArgsConstructor;
import lombok.var;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@ControllerAdvice
@RequiredArgsConstructor

@Slf4j
public class DomainExceptionAdvice {


	  @ExceptionHandler(GlobalException.class)
	  public Mono<ResponseEntity<GeneralErrorResponse>> handleDomainException(GlobalException exc, ServerWebExchange exchange) {

			final var status = exc.getHttpStatus().value();

			log.warn("handleDomainException.status:\n	{}, {}", status, exc.getMessage());

//			return Mono.just(ResponseEntity.ok(new GeneralResponse(status, exc.getMessage())));

			return Mono
					.just(ResponseEntity.status(exc.getHttpStatus()).body(new GeneralErrorResponse(status, exc.getMessage())));

	  }

}
