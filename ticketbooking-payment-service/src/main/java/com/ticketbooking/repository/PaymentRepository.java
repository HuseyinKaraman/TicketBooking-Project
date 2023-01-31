package com.ticketbooking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ticketbooking.model.Payment;
import com.ticketbooking.model.enums.PaymentStatus;

public interface PaymentRepository extends JpaRepository<Payment, Integer>{

	Optional<List<Payment>> findPaymentByUserId(Integer userId);

	@Modifying
	@Query("update Payment p set p.paymentStatus = ?1 where p.userId = ?2")
	Integer setPaymentStatusByUserId(PaymentStatus paymentStatus , Integer userId);
	
}
