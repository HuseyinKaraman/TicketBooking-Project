package com.ticketbooking.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ticketbooking.exception.ExceptionResponse;
import com.ticketbooking.exception.PaymentServiceException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(PaymentServiceException.class)
	public ResponseEntity<ExceptionResponse> handle(PaymentServiceException exception) {
		return ResponseEntity.ok(new ExceptionResponse(exception.getMessage(), HttpStatus.BAD_REQUEST));
	}
}
