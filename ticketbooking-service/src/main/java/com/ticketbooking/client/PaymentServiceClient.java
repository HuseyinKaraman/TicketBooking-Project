package com.ticketbooking.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ticketbooking.model.enums.PaymentStatus;

@FeignClient(value = "ticketbooking-payment-service", url = "http://localhost:9090/payments")
public interface PaymentServiceClient {

	
	@PostMapping
	public ResponseEntity<PaymentResponse> doPayment(@RequestBody PaymentRequest paymentRequest);
	
	@GetMapping
	public ResponseEntity<List<PaymentResponse>> getPaymentDetails();
	
	@GetMapping("/{paymentId}")
	public ResponseEntity<PaymentResponse> getPaymentDetailsById(@PathVariable Integer paymentId);
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<PaymentResponse>> getPaymentDetailsByUserId(@PathVariable Integer userId);

	@PutMapping("/{userId}")
	public ResponseEntity<Integer> updatePaymentStatusByUserId(@RequestBody PaymentStatus paymentStatus,@PathVariable Integer userId);
	
}
