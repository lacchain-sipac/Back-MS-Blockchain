package com.invest.honduras.response;

import com.everis.blockchain.honduras.Change;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentItem {
	private String hash;
	private String user;
	private String role;
	private boolean isfinal;
	private long creation;

	public DocumentItem(Change change, String hash) {
		this.hash = hash;
		this.user = change.user;
		this.role = change.roleUser;
		if (change.timestamp != null)
			this.creation = change.timestamp.longValue();
		this.isfinal = change.isFinal;
	}
	public DocumentItem() {}
}
