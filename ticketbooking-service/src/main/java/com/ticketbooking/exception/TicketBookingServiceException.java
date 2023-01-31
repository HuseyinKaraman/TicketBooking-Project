package com.ticketbooking.exception;

public class TicketBookingServiceException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2089092150344614323L;

	public TicketBookingServiceException() {
		super("Ticket Booking service exception");
	}
	
	public TicketBookingServiceException(String message) {
		super(message);
	}
	
}
