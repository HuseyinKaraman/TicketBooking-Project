package com.ticketbooking.converter;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ticketbooking.dto.request.PaymentRequest;
import com.ticketbooking.dto.response.PaymentResponse;
import com.ticketbooking.dto.response.PaymentResponse.PaymentResponseBuilder;
import com.ticketbooking.model.Payment;
import com.ticketbooking.model.Payment.PaymentBuilder;

@Component
public class PaymentConverter {

	public PaymentConverter() {
		// TODO Auto-generated constructor stub
	}

	public Payment convert(PaymentRequest paymentRequest) {
		PaymentBuilder builder = Payment.builder().id(paymentRequest.getUserId()).amount(paymentRequest.getAmount())
				.paymentType(paymentRequest.getPaymentType()).paymentDate(LocalDate.now())
				.totalTicket(paymentRequest.getTotalTicket()).userId(paymentRequest.getUserId());
		return builder.build();
	}

	public PaymentResponse convert(Payment payment) {
		PaymentResponseBuilder responseBuilder = PaymentResponse.builder().userId(payment.getUserId())
				.paymentType(payment.getPaymentType()).paymentDate(payment.getPaymentDate()).amount(payment.getAmount())
				.totalTicket(payment.getTotalTicket()).paymentStatus(payment.getPaymentStatus());
		return responseBuilder.build();
	}

	public List<PaymentResponse> convert(List<Payment> payments) {
		return payments.stream().map(this::convert).toList();
	}

}
