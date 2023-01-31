package com.ticketbooking.dto.request;

import com.ticketbooking.model.enums.PaymentType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PaymentRequest {

	private Integer userId;
	private double amount;
	private PaymentType paymentType;
	private String cardNo;
	private Integer totalTicket;
}
