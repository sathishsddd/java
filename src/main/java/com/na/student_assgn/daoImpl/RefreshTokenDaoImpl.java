package com.na.student_assgn.daoImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.na.student_assgn.dao.RefreshTokenDao;
import com.na.student_assgn.model.RefreshToken;

@Repository
public class RefreshTokenDaoImpl implements RefreshTokenDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final Logger logger = LoggerFactory.getLogger(RefreshTokenDaoImpl.class);

	@Override
	public RefreshToken findByToken(String token) {
		String query = "SELECT * FROM refresh_token WHERE token = ?";
		RowMapper<RefreshToken> rowMapper = new BeanPropertyRowMapper<RefreshToken>(RefreshToken.class);
		RefreshToken refreshToken = null;
		try {
			refreshToken =  jdbcTemplate.queryForObject(query, rowMapper, token);
		} catch (EmptyResultDataAccessException e) {
		   logger.warn(token+" : Refresh token not available in db");
		   return null;
		}
		return refreshToken;
	}

	@Override
	public int insertRefreshToken(RefreshToken refreshToken) {
		String query = "INSERT INTO refresh_token(token, expiry_date, user_id) VALUES (?,?,?)";
		return jdbcTemplate.update(query, refreshToken.getToken(), refreshToken.getExpiry_date(), refreshToken.getUser_id());

	}

	@Override
	public int deleteToken(String token) {
		String query = "DELETE FROM refresh_token WHERE token = ?";
	    return jdbcTemplate.update(query, token);
	}

}
