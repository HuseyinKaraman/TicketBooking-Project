package com.ticketbooking.client;

import java.time.LocalDate;

import com.ticketbooking.model.enums.PaymentStatus;
import com.ticketbooking.model.enums.PaymentType;

public class PaymentResponse {
	
	private Integer userId;
	private PaymentType paymentType;
	private PaymentStatus paymentStatus;
	private LocalDate paymentDate;
	private double amount;
	private Integer totalTicket;
	
	public PaymentResponse() {
		// TODO Auto-generated constructor stub
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Integer getTotalTicket() {
		return totalTicket;
	}

	public void setTotalTicket(Integer totalTicket) {
		this.totalTicket = totalTicket;
	}

}
