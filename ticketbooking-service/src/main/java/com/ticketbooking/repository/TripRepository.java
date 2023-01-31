package com.ticketbooking.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ticketbooking.model.Trip;
import com.ticketbooking.model.enums.TransportType;

public interface TripRepository extends JpaRepository<Trip, Integer>{
	
	Optional<List<Trip>> findByFromAndToAndTransportType(String from, String to, TransportType transportType);
	
	Optional<List<Trip>> findByFromAndToAndTransportTypeAndDepartureDate(String from, String to, TransportType transportType, LocalDateTime departureDate);
	
	Optional<List<Trip>> findByDepartureDate(LocalDateTime departureDate);
	
	
	@Query("SELECT SUM(price*reservedSeatCount) FROM Trip")
	Integer getTotalPrice();
	
	@Query("SELECT SUM(reservedSeatCount) FROM Trip")
	Integer countByreservedSeatCount();
	
}
