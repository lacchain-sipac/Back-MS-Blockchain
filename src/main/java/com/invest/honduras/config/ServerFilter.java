package com.invest.honduras.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.invest.honduras.client.LoginClient;
import com.invest.honduras.component.impl.ServerConfig;

import reactor.core.publisher.Mono;

@Component
public class ServerFilter implements WebFilter {

	@Autowired
	ServerConfig serverConfig;
	
	
	@Autowired
	 LoginClient loginClient;
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse response = exchange.getResponse();

		serverConfig.setRequest(request);
		serverConfig.setResponse(response);
        
	//	String path = request.getPath().value();
		
		//log.info(path);
				
		if(request.getHeaders().containsKey("Authorization") ) {
			
			String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
			return loginClient.refreshToken(authHeader).flatMap(resp -> {				
		        exchange.getResponse()
		          .getHeaders().add("new-token", resp.getDataResponse().getToken());
				return chain.filter(exchange);				
				
			});
			
			
		}else {
			return chain.filter(exchange);
		}	
		

	}

}
