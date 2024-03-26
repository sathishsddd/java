package com.na.student_assgn.dto;

public class AuthenticateResponse {
	
	private String refreshToken;

	public AuthenticateResponse() {
		super();
	}

	public AuthenticateResponse(String refreshToken) {
		super();
		this.refreshToken = refreshToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	

}
