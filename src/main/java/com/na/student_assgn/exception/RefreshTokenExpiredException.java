package com.na.student_assgn.exception;

public class RefreshTokenExpiredException extends RuntimeException {
	
	public RefreshTokenExpiredException(String msg) {
		super(msg);
	}

}
