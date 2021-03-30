package com.invest.honduras.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseItem {

	private String result;
	
	public ResponseItem() {
		
	}
	
	public ResponseItem(String item) {
		result = item;
	}
}
