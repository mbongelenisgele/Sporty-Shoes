package com.simplilearn.controller;

import java.time.LocalDate;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.simplilearn.exception.OrderException;
import com.simplilearn.exception.ProductCategoryException;
import com.simplilearn.exception.ProductException;
import com.simplilearn.exception.UserException;
import com.simplilearn.exception.UserRoleException;
import com.simplilearn.entity.ErrorInfo;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// This method will handle all custom Exceptions UserException, ProductException, OrderException, UserRoleException and ProductCategoryException
	@ExceptionHandler({ OrderException.class, ProductException.class, UserException.class, UserRoleException.class, ProductCategoryException.class })
	public ResponseEntity<ErrorInfo> customExceptionHandler(Exception exception) {
		ErrorInfo errorResponse = new ErrorInfo(HttpStatus.BAD_REQUEST.value() + " : BAD_REQUEST", exception.getMessage(),
				LocalDate.now());
		return new ResponseEntity<ErrorInfo>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	// This method will handle all general exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorInfo> generalExceptionHandler(Exception exception) {
		String message = "Some error occured. Please contact administrator. ";
		ErrorInfo errorResponse = new ErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR.value() + " : INTERNAL_SERVER_ERROR",
				message + exception.getMessage(), LocalDate.now());
		return new ResponseEntity<ErrorInfo>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// This method will handle Argument Validation Exceptions
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorInfo> exceptionHandler(MethodArgumentNotValidException exception) {
		String errorMessage = exception.getBindingResult().getAllErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.joining(", "));

		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value()+ " : BAD_REQUEST");
		errorInfo.setErrorMessage(errorMessage);
		errorInfo.setTimeStamp(LocalDate.now());

		return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.BAD_REQUEST);
	}
}
