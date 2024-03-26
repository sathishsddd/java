package com.na.student_assgn.controllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.na.student_assgn.exception.DataToExcelException;
import com.na.student_assgn.exception.EmptyStudentListException;
import com.na.student_assgn.exception.InvalidPhoneNumberException;
import com.na.student_assgn.exception.InvalidRowValueException;
import com.na.student_assgn.exception.InvalidSortingOrderException;
import com.na.student_assgn.exception.RefreshTokenExpiredException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;

@RestControllerAdvice
public class StudentControllerAdvice {
	
	private static final Logger logger = LoggerFactory.getLogger(StudentControllerAdvice.class);
	
	@ExceptionHandler(InvalidPhoneNumberException.class)
	public ResponseEntity<String> handleInvalidPhoneNumberException(InvalidPhoneNumberException exception){
		logger.warn(exception.getMessage());
		return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
		Map<String, String> errors=new HashMap<>();
		exception.getBindingResult().getAllErrors().forEach((error) ->{
			String field=((FieldError) error).getField();
			String message=error.getDefaultMessage();
			logger.warn(field+"-"+message);
			errors.put(field, message);
		});
		
		return new ResponseEntity<Object>(errors,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<String> handleEmptyResultDataAccessException(EmptyResultDataAccessException exception){
		logger.warn(exception.getMessage());
		return new ResponseEntity<String>("No data available",HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException exception){
		logger.warn(exception.getMessage());
		return new ResponseEntity<String>("Invalid Credential",HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(RefreshTokenExpiredException.class)
	public ResponseEntity<String> handleRefreshTokenExpiredException(RefreshTokenExpiredException exception){
		logger.warn(exception.getMessage());
		return new ResponseEntity<String>("Session Expired.",HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<String> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException exception){
		logger.warn(exception.getMessage());
		return new ResponseEntity<String>(exception.getMessage(),HttpStatus.BAD_REQUEST);
				
	}
	
	@ExceptionHandler(EmptyStudentListException.class)
	public ResponseEntity<String> handleEmptyStudentListException(EmptyStudentListException exception) {
		logger.warn(exception.getMessage());
		return new ResponseEntity<String>(exception.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DataToExcelException.class)
	public ResponseEntity<String> handleDataToExcelException(DataToExcelException exception) {
		logger.warn(exception.getMessage());
		return new ResponseEntity<String>("Internal Server error",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(InvalidSortingOrderException.class)
	public ResponseEntity<String> handleInvalidSortingOrderException(InvalidSortingOrderException exception){
		logger.warn(exception.getMessage());
		return new ResponseEntity<String>(exception.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
//	@ExceptionHandler({ AuthenticationException.class })
//    public ResponseEntity<String> handleAuthenticationException(Exception exception) {
//		logger.warn(exception.getMessage());
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
//    }
	
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException exception){
		logger.warn(exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Session Expired.");
	}
	
	@ExceptionHandler(SignatureException.class)
	public ResponseEntity<String> handleSignatureException(SignatureException exception){
		logger.warn(exception.getMessage());
		return new ResponseEntity<String>("Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<String> handleNullPointerException(NullPointerException exception){
		logger.warn(exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error.");
	}
	
	@ExceptionHandler(InvalidRowValueException.class)
	public ResponseEntity<String> handleInvalidRowValueException(InvalidRowValueException exception){
		logger.warn(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
	}

}
