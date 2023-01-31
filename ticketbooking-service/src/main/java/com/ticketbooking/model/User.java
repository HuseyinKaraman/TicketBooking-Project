package com.ticketbooking.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ticketbooking.model.enums.UserType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "email",unique = true,nullable = false)
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "userType")
	@Enumerated(EnumType.STRING)
	private UserType userType;
	
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@Column(name = "create_date")
	private LocalDateTime createDate;
	
	@Column(name = "mobile_number")
	private String mobileNumber;
	
	@JsonIgnore
	@Builder.Default
	@OneToMany(mappedBy = "user",fetch=FetchType.LAZY)
	private List<Ticket> tickets = new ArrayList<>(); 
	
}
