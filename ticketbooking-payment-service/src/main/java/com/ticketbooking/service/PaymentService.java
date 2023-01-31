package com.ticketbooking.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.ticketbooking.controller.PaymentController;
import com.ticketbooking.converter.PaymentConverter;
import com.ticketbooking.dto.request.PaymentRequest;
import com.ticketbooking.dto.response.PaymentResponse;
import com.ticketbooking.exception.PaymentServiceException;
import com.ticketbooking.model.Payment;
import com.ticketbooking.model.enums.PaymentStatus;
import com.ticketbooking.model.enums.PaymentType;
import com.ticketbooking.repository.PaymentRepository;


@Service
public class PaymentService {

	private PaymentRepository paymentRepository;
	private PaymentConverter converter;
	final private Logger logger = Logger.getLogger(PaymentController.class.getName()); 
	
	public PaymentService(PaymentRepository paymentRepository, PaymentConverter converter) {
		this.paymentRepository = paymentRepository;
		this.converter = converter;
	}

	
	public PaymentResponse doPayment(PaymentRequest paymentRequest) {
		Payment payment = converter.convert(paymentRequest);
		
		PaymentStatus status = PaymentStatus.PENDING;
		if (PaymentType.CREDITCARD.equals(payment.getPaymentType())) {
			status = PaymentStatus.SUCCESS;
		}
		try {		
			payment.setPaymentStatus(status);
			payment = paymentRepository.save(payment);
		} catch (Exception e) {
			status = PaymentStatus.FAILED;
			logger.log(Level.WARNING, "[TicketBooking-Payment-Service] -> Payment Failed: User ID: {0} , datetime: {1}", new Object[] { payment.getUserId(),LocalDateTime.now()});
			payment.setPaymentStatus(status);
		}
		
		logger.log(Level.INFO, "[TicketBooking-Payment-Service] -> Payment: User ID: {0} , status: {1} , datetime: {2}", new Object[] { payment.getUserId(),status,LocalDateTime.now()});
		return converter.convert(payment);
	} 
	
	 public PaymentResponse getPaymentDetailsById(Integer paymentId) {
		 return converter.convert(paymentRepository.findById(paymentId).orElseThrow(()-> new PaymentServiceException("Payment with ID not found")));
	 }
	
	 public List<PaymentResponse> getPaymentDetails() {
		 return converter.convert(paymentRepository.findAll());
	 }
	 
	 public List<PaymentResponse> getPaymentDetailsByUserId(Integer userId) {
		 return converter.convert(paymentRepository.findPaymentByUserId(userId).orElseThrow(()-> new PaymentServiceException("Payments with userID not found")));
	 }
	 
	 public Integer updatePaymentStatusByUserId(PaymentStatus paymentStatus , Integer userId) {
		return paymentRepository.setPaymentStatusByUserId(paymentStatus, userId);
	}
	 
}
