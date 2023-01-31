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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ticketbooking.model.enums.TransportType;
import com.ticketbooking.model.enums.TripStatus;

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
@Table(name = "trip")
public class Trip {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Column(name = "from_city")
	private String from;
	
	@Column(name = "to_city")
	private String to;
	
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@Column(name = "departure_date")
	private LocalDateTime departureDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "transport_type")
	private TransportType transportType; 
	
	@Enumerated(EnumType.STRING)
	@Column(name = "trip_status")
	private TripStatus tripStatus;
	
	@Column(name = "capacity")
	private Integer capacity;
	
	@Default
	@Column(name = "reserved_seat_count")
	private Integer reservedSeatCount=0;
	
	@Column(name = "price")
	private Integer price;
	
	@JsonIgnore
	@Default
	@OneToMany(mappedBy = "trip",fetch=FetchType.LAZY)
	private List<Ticket> tickets = new ArrayList<>();
	
}
