package com.ticketbooking.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.ticketbooking.client.PaymentRequest;
import com.ticketbooking.client.PaymentResponse;
import com.ticketbooking.client.PaymentServiceClient;
import com.ticketbooking.configuration.TicketBookingTicketQueue;
import com.ticketbooking.controller.TicketController;
import com.ticketbooking.converter.TicketConverter;
import com.ticketbooking.exception.TicketBookingServiceException;
import com.ticketbooking.model.Ticket;
import com.ticketbooking.model.Trip;
import com.ticketbooking.model.User;
import com.ticketbooking.model.enums.Gender;
import com.ticketbooking.model.enums.PaymentStatus;
import com.ticketbooking.model.enums.PaymentType;
import com.ticketbooking.model.enums.UserType;
import com.ticketbooking.repository.TicketRepository;

@Service
public class TicketService {

	private static final int INDIVUAL_USER_LIMIT_FOR_ONE_TRIP = 5;
	private static final int INDIVUAL_USER_MALE_PASSENGER_LIMIT = 2;
	private static final int CORPARETE_USER_LIMIT_FOR_ONE_TRIP = 20;

	private TicketRepository ticketRepository;
	private UserService userService;
	private TripService tripService;
	private PaymentServiceClient paymentServiceClient;
	private RabbitTemplate rabbitTemplate;
	private TicketBookingTicketQueue ticketQueue;
	private TicketConverter ticketConverter;
	final private Logger logger = Logger.getLogger(TicketController.class.getName());

	public TicketService(TicketRepository ticketRepository, UserService userService, TripService tripService,
			PaymentServiceClient paymentServiceClient, RabbitTemplate rabbitTemplate,
			TicketBookingTicketQueue ticketQueue, TicketConverter ticketConverter) {
		this.ticketRepository = ticketRepository;
		this.userService = userService;
		this.tripService = tripService;
		this.paymentServiceClient = paymentServiceClient;
		this.rabbitTemplate = rabbitTemplate;
		this.ticketQueue = ticketQueue;
		this.ticketConverter = ticketConverter;
	}

	/**@Note: Yeni ticket oluşturur!*/
	public List<Ticket> create(List<Ticket> tickets, Integer userId, Integer tripId, PaymentType paymentType) {
		User user = userService.findById(userId);
		Trip trip = tripService.findById(tripId);

		checkTicketLimit(tickets, user, trip);
		tripService.checkTripSeatLimit(tickets, user, trip);
		PaymentStatus status = usePaymentClient(tickets, trip.getPrice(), paymentType, user.getId());

		tickets.forEach(ticket -> {
			ticket.setUser(user);
			ticket.setTrip(trip);
			ticket.setPaymentStatus(status);
		});
		/** @NOTE: send ticket details to rabbitmq if paymentStatus is success */
		if (PaymentStatus.SUCCESS.equals(status)) {
			logger.log(Level.WARNING, "[createTicket] - userId: {0}, sent to : {1}",
					new Object[] { user.getId(), ticketQueue.getTicketQueueName() });
			rabbitTemplate.convertAndSend(ticketQueue.getTicketQueueName(), ticketConverter.convert(tickets));
		}
		
		List<Ticket> saveAll = ticketRepository.saveAll(tickets);
		
		saveAll.forEach(saved->logger.log(Level.INFO, "[createTicket] - userId: {0}, ticketId : {1}", new Object[] { user.getEmail(), saved.getId()}));
		return saveAll;
	}

	/** @Note: kayıtlı ticket'ları verir */
	public List<Ticket> findAll() {
		return ticketRepository.findAll();
	}

	/** @Note: Girilen Id'e ait ticket'i doner */
	public Ticket findById(Integer ticketId) {
		return ticketRepository.findById(ticketId).orElseThrow(()->new TicketBookingServiceException("Ticket not found!"));
	}

	/** @Note: Girilen UserId'e ait ticketları'i doner */
	public List<Ticket> findByUserId(Integer userId) {
		return ticketRepository.findByUserId(userId)
				.orElseThrow(() -> new TicketBookingServiceException("UserNot found!"));
	}

	
	/**@Note: bileti alan user'in gereksenimlerine bakılır*/
 	private void checkTicketLimit(List<Ticket> tickets, User user, Trip trip) {
		/** @Note: Aynı sefer için daha önce bilet alınmışmı ? */
		Integer bookedTicketSize = ticketRepository.countByUserIdAndTripId(user.getId(), trip.getId()).orElse(0);
		int ticketSize = bookedTicketSize + tickets.size(); // aynı seferiçin onceden alınan bilet sayısı + yeni alınan bilet sayısı
		
		if (UserType.INDIVIDUAL.equals(user.getUserType())) {
			 /** @Note: alınan bilette kaç tane erkek yolcu var ?*/
			long count = tickets.stream().filter(ticket -> Gender.MALE.equals(ticket.getGender())).count();
			
			if (ticketSize > INDIVUAL_USER_LIMIT_FOR_ONE_TRIP) {
				logger.log(Level.WARNING, "The individual user can buy a maximum of 5 tickets for a trip. userID : {0}",user.getId());
				throw new TicketBookingServiceException(user.getUserType() + " user, valid limit exceeded. userId:" + user.getId());
			} 
			else if (count > INDIVUAL_USER_MALE_PASSENGER_LIMIT) {
				logger.log(Level.WARNING,"The individual user can buy a maximum of 2 men's passengers in a single order. userID : {0}",user.getId());
				throw new TicketBookingServiceException(user.getUserType() + " user, male passenger max size should be 2. userId:" + user.getId());
			}
		} 
		else if (UserType.CORPARETE.equals(user.getUserType())) {
			if (ticketSize > CORPARETE_USER_LIMIT_FOR_ONE_TRIP) {
				logger.log(Level.WARNING, "Corporate user can buy up to 20 tickets for the same trip. userID : {0}",user.getId());
				throw new TicketBookingServiceException(user.getUserType() + " user, valid limit exceeded. userId " + user.getId());
			} 
		}
	}

 	/**@Note: Payment-Service'i kullanır*/
	private PaymentStatus usePaymentClient(List<Ticket> tickets, Integer tripPrice, PaymentType paymentType,
			Integer userId) {
		PaymentRequest paymentRequest = PaymentRequest.builder().amount(tickets.size() * tripPrice).userId(userId)
				.totalTicket(tickets.size()).paymentType(paymentType).cardNo("123456").build();

		PaymentResponse paymentResponse = paymentServiceClient.doPayment(paymentRequest).getBody();
		return paymentResponse.getPaymentStatus();
	}

}
