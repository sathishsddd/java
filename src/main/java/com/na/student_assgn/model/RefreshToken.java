package com.na.student_assgn.model;

import java.time.Instant;

public class RefreshToken {
	
	private Integer token_id;
	
	private String token;
	
	private Instant expiry_date;
	
	private Integer user_id;

	public RefreshToken() {
		super();
	}

	public RefreshToken(Integer token_id, String token, Instant expiry_date, Integer user_id) {
		super();
		this.token_id = token_id;
		this.token = token;
		this.expiry_date = expiry_date;
		this.user_id = user_id;
	}

	public Integer getToken_id() {
		return token_id;
	}

	public void setToken_id(Integer token_id) {
		this.token_id = token_id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Instant getExpiry_date() {
		return expiry_date;
	}

	public void setExpiry_date(Instant expiry_date) {
		this.expiry_date = expiry_date;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	

}
