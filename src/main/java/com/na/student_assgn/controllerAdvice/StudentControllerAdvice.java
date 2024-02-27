package com.na.student_assgn.controllerAdvice;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.na.student_assgn.exception.InvalidPhoneNumberException;

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

}
