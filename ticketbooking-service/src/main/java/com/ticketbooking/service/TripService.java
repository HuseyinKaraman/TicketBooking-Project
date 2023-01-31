package com.ticketbooking.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.ticketbooking.controller.TripController;
import com.ticketbooking.converter.TripConverter;
import com.ticketbooking.dto.request.UpdateTripRequest;
import com.ticketbooking.exception.TicketBookingServiceException;
import com.ticketbooking.model.Ticket;
import com.ticketbooking.model.Trip;
import com.ticketbooking.model.User;
import com.ticketbooking.model.enums.TransportType;
import com.ticketbooking.repository.TripRepository;

@Service
public class TripService {

	private final static int BUS_CAPACITY = 45;
	private final static int FLIGHT_CAPACITY = 189;
	
	private TripRepository tripRepository;
	private TripConverter tripConverter;
	private UserService userService;
	private final Logger logger = Logger.getLogger(TripController.class.getName());

	public TripService(TripRepository tripRepository, TripConverter tripConverter, UserService userService) {
		this.tripRepository = tripRepository;
		this.tripConverter = tripConverter;
		this.userService = userService;
	}

	/** @Note: yeni trip oluşturulır eger user admin ise */
	public Trip create(Trip trip, int userId) {
		userService.isAdminUser(userId);
		if (TransportType.BUS.equals(trip.getTransportType())) {
			trip.setCapacity(BUS_CAPACITY);
		} else {
			trip.setCapacity(FLIGHT_CAPACITY);
		}

		Trip save = tripRepository.save(trip);
		logger.log(Level.INFO, "[createTrip] - trip created: {0}", trip.getId());
		return save;
	}

	/** @Note: var olan butun tripleri döner */
	public List<Trip> findAll() {
		return tripRepository.findAll();
	}

	/** @Note: Girilen Id'e ait trip'i doner */
	public Trip findById(int tripId) {
		return tripRepository.findById(tripId).orElseThrow(() -> new TicketBookingServiceException("Not found Trip by Id!"));
	}

	/** @Note: var olan trip güncellenir eger user admin ise */
	public Trip update(UpdateTripRequest updateTripRequest, int userId) {
		userService.isAdminUser(userId);
		Trip trip = findById(updateTripRequest.getId());
		Trip updateTrip = tripConverter.converter(trip, updateTripRequest);
		logger.log(Level.INFO, "[updateTrip] - trip updated: {0}", trip.getId());
		return tripRepository.save(updateTrip);
	}

	/** @Note: var olan trip silinir */
	public void delete(int tripId, int userId) {
		userService.isAdminUser(userId);
		tripRepository.deleteById(tripId);
	} 

	/** @Note: trip'in kapasitesi test edilir */
	public void checkTripSeatLimit(List<Ticket> tickets,User user, Trip trip) {
		/** @Note: Seat number Control */
		tickets.forEach(ticket -> {
			if (trip.getCapacity() < ticket.getSeatNumber()) {
				logger.log(Level.WARNING, "[TripService] -> [checkTripSeatLimit] : Invalid Seat number, tripId:{0}, userId: {1}",new Object[] {trip.getId(), user.getId() });
				throw new TicketBookingServiceException("Invalid Seat number!");				
			}
		});

		/** @Note: capacity Control */
		if (trip.getCapacity() == trip.getReservedSeatCount()) {
			logger.log(Level.WARNING, "[TripService] -> [checkTripSeatLimit] : Trip seat is full, tripId:{0}, userId: {1}",new Object[] {trip.getId(), user.getId() });
			throw new TicketBookingServiceException("Trip seat is full!");
		} else if (trip.getCapacity() < (trip.getReservedSeatCount() + tickets.size())) {
			logger.log(Level.WARNING, "[TripService] -> [checkTripSeatLimit] : insufficient number of seats, tirpId:{0} userId  {1}",new Object[] {trip.getId(), user.getId() });
			throw new TicketBookingServiceException("insufficient number of seats!");
		}

		trip.setReservedSeatCount(tickets.size() + trip.getReservedSeatCount());
	}

	/** @Note: toplam alınan bilet sayısını ve toplam price'i verir */
	public String tripInfo() {
		Integer totalReservedSeatCount = tripRepository.countByreservedSeatCount();
		Integer totalPrice = tripRepository.getTotalPrice();
		return "{ Total Reserved Seat Count : " +totalReservedSeatCount + " \n TotalPrice: "+ totalPrice +" }";
	}
	
	/** @Note: from city , to city  ve transport tipine göre trip aranır veya zamanda dahil edilebilir */
	public List<Trip> searchTrip(String from, String to, TransportType transportType, LocalDateTime departureDate) {
		List<Trip> trips = null;
		if (Objects.nonNull(departureDate)) {
			trips = tripRepository.findByFromAndToAndTransportTypeAndDepartureDate(from, to, transportType,departureDate).orElseThrow(
					() -> new TicketBookingServiceException("No trip according to the conditions entered!"));
		} else {
			trips = tripRepository.findByFromAndToAndTransportType(from, to, transportType).orElseThrow(
					() -> new TicketBookingServiceException("No trip according to the conditions entered!"));
		}
		if (trips.isEmpty()) {
			logger.log(Level.WARNING, "[TripService] -> [searchTrip]: No trip according to the conditions entered! from: {0} , to: {1}, transportType: {2}", 
					new Object[] { from,to,transportType});
			throw new TicketBookingServiceException("No trip according to the conditions entered!");
		}
		return trips;
	}

	/** @Note: girilen zamana uygun tripleri döner */
	public List<Trip> searchTrip(LocalDateTime departureDate) {
		return tripRepository.findByDepartureDate(departureDate)
				.orElseThrow(() -> new TicketBookingServiceException("No trip according to the conditions entered!"));
	}

}
