package com.na.student_assgn.service;

import java.time.Instant;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.na.student_assgn.dao.RefreshTokenDao;
import com.na.student_assgn.dao.StudentDAO;
import com.na.student_assgn.exception.RefreshTokenExpiredException;
import com.na.student_assgn.model.RefreshToken;
import com.na.student_assgn.model.Student;

@Service
public class RefreshTokenService {

	private static final Logger logger = LoggerFactory.getLogger(RefreshTokenService.class);

	@Autowired
	private RefreshTokenDao refreshTokenDao;

	@Autowired
	private StudentDAO studentDAO;

	public String insertRefreshToken(String userName) {
		Student student = studentDAO.findStudentByName(userName);
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setToken(UUID.randomUUID().toString());
		refreshToken.setExpiry_date(Instant.now().plusMillis(1000*60*60*10));
		refreshToken.setUser_id(student.getStudent_id());
		int result = refreshTokenDao.insertRefreshToken(refreshToken);
		if (result > 0) {
			logger.info("Refresh token created successfully");
			return refreshToken.getToken();
		} else {
			logger.warn("Refresh token not created.");
			return null;
		}
	}

	public RefreshToken findBytoken(String token) {
		RefreshToken refreshToken = refreshTokenDao.findByToken(token);
		if (refreshToken != null) {
			logger.info("Refresh token fetched successfully");
			return refreshToken;
		} else {
			logger.warn("Refresh token not found for :" + token);
			throw new RefreshTokenExpiredException(" Refresh token was expired.");
		}
	}

	public boolean verifyRefreshToken(RefreshToken refreshToken) {
		if (refreshToken.getExpiry_date().compareTo(Instant.now()) < 0) {
			refreshTokenDao.deleteToken(refreshToken.getToken());
			logger.warn("Refresh token was expired.");
			throw new RefreshTokenExpiredException(" Refresh token was expired.");
		}else {
			logger.info("Refresh token verified.");
			return true;
		}
	}

}
