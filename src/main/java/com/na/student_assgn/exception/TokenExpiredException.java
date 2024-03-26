package com.na.student_assgn.exception;

public class TokenExpiredException extends RuntimeException {
	
	public TokenExpiredException(String msg) {
		super(msg);
	}

}
