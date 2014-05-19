package com.community.babysit.util.enums;

public enum SqlStatusEnum {
	
	STATUS_USER_NOT_FOUND(-1),
	STATUS_USER_ALREADY_EXISTS(-2),
	STATUS_FAILED_CREATE_USER(-3),
	STATUS_INVALID_USER_OR_PASS(0),
	STATUS_OK(1);

	int status;
	
	private SqlStatusEnum(int status) {
		this.status = status;
	}
	
	public int getStatus() {
		return this.status;
	}
}
