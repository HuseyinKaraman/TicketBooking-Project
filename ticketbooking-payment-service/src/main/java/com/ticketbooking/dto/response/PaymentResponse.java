package com.ticketbooking.dto.response;

import java.time.LocalDate;

import com.ticketbooking.model.enums.PaymentStatus;
import com.ticketbooking.model.enums.PaymentType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentResponse {
	
	private Integer userId;
	private PaymentType paymentType;
	private PaymentStatus paymentStatus;
	private LocalDate paymentDate;
	private double amount;
	private Integer totalTicket;
}
