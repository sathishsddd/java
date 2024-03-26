package com.na.student_assgn.dao;

import com.na.student_assgn.model.RefreshToken;

public interface RefreshTokenDao {
	
	public RefreshToken findByToken(String token);
	
	public int insertRefreshToken(RefreshToken refreshToken);
	
	public int deleteToken(String token);

}
