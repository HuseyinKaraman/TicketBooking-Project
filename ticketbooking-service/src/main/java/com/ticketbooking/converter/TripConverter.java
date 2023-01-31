package com.ticketbooking.converter;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.ticketbooking.dto.request.UpdateTripRequest;
import com.ticketbooking.model.Trip;

@Component
public class TripConverter {

	
	public TripConverter() {
		// TODO Auto-generated constructor stub
	}
	
	public Trip converter(Trip trip, UpdateTripRequest updateTripRequest) {
		trip.setPrice(Objects.nonNull(updateTripRequest.getPrice()) ? updateTripRequest.getPrice() : trip.getPrice());
		trip.setDepartureDate(Objects.nonNull(updateTripRequest.getDepartureDate()) ? updateTripRequest.getDepartureDate() : trip.getDepartureDate());
		trip.setTripStatus(Objects.nonNull(updateTripRequest.getTripStatus()) ? updateTripRequest.getTripStatus() : trip.getTripStatus());
		trip.setFrom(Objects.nonNull(updateTripRequest.getFrom()) ? updateTripRequest.getFrom() : trip.getFrom() );
		trip.setTo(Objects.nonNull(updateTripRequest.getTo()) ? updateTripRequest.getTo() : trip.getTo() );
		return trip;
	}
	

}
