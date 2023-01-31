package com.ticketbooking.exception;

public class NotificationException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4701711943911498113L;

	public NotificationException() {
		this("Notification Exception");
	}	
	
	public NotificationException(String msg) {
		super(msg);
	}
	
}
