package com.na.student_assgn.exception;

public class InvalidPhoneNumberException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public InvalidPhoneNumberException(String msg) {
		super(msg);
	}

}
