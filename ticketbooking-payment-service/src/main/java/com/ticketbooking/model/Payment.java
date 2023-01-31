package com.ticketbooking.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
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
@Entity
@Table(name = "payment")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "user_id")
	private Integer userId;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_type")
	private PaymentType paymentType;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_status")
	private PaymentStatus paymentStatus;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@Column(name = "payment_date")
	private LocalDate paymentDate;

	@Column(name = "total_ticket")
	private Integer totalTicket;
	
	@Column(name = "amount")
	private double amount;

}
