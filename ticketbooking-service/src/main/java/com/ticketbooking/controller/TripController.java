package com.ticketbooking.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ticketbooking.dto.request.UpdateTripRequest;
import com.ticketbooking.model.Trip;
import com.ticketbooking.model.enums.TransportType;
import com.ticketbooking.service.TripService;

@RestController
@RequestMapping("/trips")
public class TripController {

	private TripService tripService;

	public TripController(TripService tripService) {
		this.tripService = tripService;
	}

	/** @Note: yeni trip oluşturulır eger user admin ise */
	@PostMapping
	public ResponseEntity<Trip> create(@RequestBody Trip trip, @RequestParam Integer userId) {
		return new ResponseEntity<>(tripService.create(trip, userId), HttpStatus.CREATED);
	}

	/** @Note: var olan butun tripleri döner */
	@GetMapping
	public ResponseEntity<List<Trip>> getAll() {
		return ResponseEntity.ok(tripService.findAll());
	}

	/** @Note: from city , to city  ve transport tipine göre trip aranır veya zamanda dahil edilebilir */
	@GetMapping(value = "/search")
	public ResponseEntity<List<Trip>> searchTrip(@RequestParam String from, @RequestParam String to,
			@RequestParam TransportType transportType,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) {
		return ResponseEntity.ok(tripService.searchTrip(from, to, transportType, dateTime));
	}

	/** @Note: girilen zamana uygun tripleri döner */
	@GetMapping(value = "/searchByDate")
	public ResponseEntity<List<Trip>> searchTrip(@RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) {
		return ResponseEntity.ok(tripService.searchTrip(dateTime));
	}
	
	/** @Note: var olan trip güncellenir eger user admin ise */
	@PostMapping(value = "/update")
	public ResponseEntity<Trip> update(@RequestBody UpdateTripRequest updateTripRequest,@RequestParam int userId) {
		return new ResponseEntity<>(tripService.update(updateTripRequest, userId),HttpStatus.OK);
	}

	/** @Note: var olan trip silinir eger user admin ise*/
	@PostMapping(value = "/delete")
	public ResponseEntity<HttpStatus> delete(@RequestParam int userId, @RequestParam int tripId) {
		tripService.delete(tripId, userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/** @Note: toplam alınan bilet sayısını ve toplam price'i verir */
	@GetMapping(value = "/info")
	public ResponseEntity<String> getInfo() {
		return ResponseEntity.ok(tripService.tripInfo());
	}


}
