package com.db.advice;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.db.exception.AccountException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(AccountException.class)
	public ResponseEntity<String> handleAccountException(AccountException accountException){
		return new  ResponseEntity<String>(accountException.getMessage(),accountException.getHttpStatus());
	}

}
