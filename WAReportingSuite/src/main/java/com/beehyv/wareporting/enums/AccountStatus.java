package com.beehyv.wareporting.enums;

public enum AccountStatus {
	ACTIVE("ACTIVE"),
	PENDING("PENDING_ACTIVATION"),
	INACTIVE("INACTIVE");
	
	private String accountStatus;
	
	AccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	
	public String getAccountStatus() {
		return accountStatus;
	}
}
