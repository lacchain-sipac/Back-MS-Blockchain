package com.invest.honduras.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseBoolItem {

	private Boolean result;

	public ResponseBoolItem(boolean flag) {
		result=flag;
	}

	public ResponseBoolItem() {
	}
}
