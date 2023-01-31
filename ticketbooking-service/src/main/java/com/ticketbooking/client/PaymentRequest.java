package com.ticketbooking.client;

import com.ticketbooking.model.enums.PaymentType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentRequest {
	private Integer userId;
	private double amount;
	private PaymentType paymentType;
	private String cardNo;
	private Integer totalTicket;

}
