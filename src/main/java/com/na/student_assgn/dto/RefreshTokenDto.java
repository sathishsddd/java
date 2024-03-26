package com.na.student_assgn.dto;

public class RefreshTokenDto {
	
	private String token;

	public RefreshTokenDto() {
		super();
	}

	public RefreshTokenDto(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
