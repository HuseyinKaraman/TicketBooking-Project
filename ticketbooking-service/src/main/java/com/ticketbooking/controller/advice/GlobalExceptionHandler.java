package com.ticketbooking.controller.advice;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ticketbooking.exception.ExceptionResponse;
import com.ticketbooking.exception.TicketBookingServiceException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	
	@ExceptionHandler(TicketBookingServiceException.class)
	public ResponseEntity<ExceptionResponse> handle(TicketBookingServiceException exception) {
		return ResponseEntity.ok(new ExceptionResponse(exception.getMessage(), HttpStatus.BAD_REQUEST));
	}
	
}
