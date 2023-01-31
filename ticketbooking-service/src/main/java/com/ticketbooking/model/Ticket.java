package com.ticketbooking.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.ticketbooking.model.enums.Gender;
import com.ticketbooking.model.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ticket",uniqueConstraints = { @UniqueConstraint(columnNames = { "trip_id", "seat_number" })} )
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String name;
	
	@Column(name = "surname")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String surname;
	
	@Column(name = "identity_number",nullable = false)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String identityNumber;
		
	@JsonFormat(pattern = "dd-MM-yyyy") 
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@Column(name = "birth_date")
	private LocalDate birthDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "gender")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Gender gender;
	
	@Column(name = "seat_number")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer seatNumber;
	
	
	@Enumerated(EnumType.STRING)
	@Column(name = "payment_status")
	private PaymentStatus paymentStatus;
	
	@Default
	@ManyToOne
	@JoinColumn(name = "trip_id")
	private Trip trip = new Trip();
	
	@Default
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user = new User();

}
