package com.ticketbooking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticketbooking.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

	Optional<Integer> countByUserIdAndTripId(Integer userId,Integer tripId);
	
	Optional<List<Ticket>> findByUserId(Integer userId);
}
