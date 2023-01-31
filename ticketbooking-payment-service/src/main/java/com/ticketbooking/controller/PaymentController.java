package com.ticketbooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticketbooking.dto.request.PaymentRequest;
import com.ticketbooking.dto.response.PaymentResponse;
import com.ticketbooking.model.enums.PaymentStatus;
import com.ticketbooking.service.PaymentService;

@RestController
@RequestMapping("/payments")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@PostMapping
	public ResponseEntity<PaymentResponse> doPayment(@RequestBody PaymentRequest paymentRequest) {
		return new ResponseEntity<>(paymentService.doPayment(paymentRequest), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<PaymentResponse>> getPaymentDetails() {
		return new ResponseEntity<>(paymentService.getPaymentDetails(), HttpStatus.OK);
	}

	@GetMapping("/{paymentId}")
	public ResponseEntity<PaymentResponse> getPaymentDetailsById(@PathVariable Integer paymentId) {
		return new ResponseEntity<>(paymentService.getPaymentDetailsById(paymentId), HttpStatus.OK);
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<PaymentResponse>> getPaymentDetailsByUserId(@PathVariable Integer userId) {
		return new ResponseEntity<>(paymentService.getPaymentDetailsByUserId(userId), HttpStatus.OK);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<Integer> updatePaymentStatusByUserId(@RequestBody PaymentStatus paymentStatus,@PathVariable Integer userId) {
		return new ResponseEntity<>(paymentService.updatePaymentStatusByUserId(paymentStatus, userId), HttpStatus.OK);
	}

}
