package com.ticketbooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ticketbooking.model.Ticket;
import com.ticketbooking.model.enums.PaymentType;
import com.ticketbooking.service.TicketService;

@RestController
@RequestMapping("/tickets")
public class TicketController {

	@Autowired
	private TicketService ticketService;

	/**@Note: Yeni ticket oluşturur!*/
	@PostMapping
	public ResponseEntity<List<Ticket>> create(@RequestBody List<Ticket> tickets, @RequestParam Integer userId,
			@RequestParam Integer tripId, @RequestParam(defaultValue = "BANKTRANSFER") PaymentType paymentType) {
		return new ResponseEntity<>(ticketService.create(tickets, userId, tripId, paymentType), HttpStatus.CREATED);
	}

	/** @Note: kayıtlı tüm ticket'ları verir */
	@GetMapping
	public ResponseEntity<List<Ticket>> getAll() {
		return  ResponseEntity.ok(ticketService.findAll());
	}

	/** @Note: Girilen UserId'e ait ticketları'i doner */
	@GetMapping(value = "/{id}")
	public ResponseEntity<List<Ticket>> getByUserId(@PathVariable Integer id) {
		return ResponseEntity.ok(ticketService.findByUserId(id));
	}

	
}
