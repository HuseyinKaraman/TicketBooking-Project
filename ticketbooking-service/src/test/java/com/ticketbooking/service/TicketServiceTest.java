package com.ticketbooking.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ticketbooking.client.PaymentServiceClient;
import com.ticketbooking.configuration.TicketBookingTicketQueue;
import com.ticketbooking.converter.TicketConverter;
import com.ticketbooking.repository.TicketRepository;

@ExtendWith(SpringExtension.class)
public class TicketServiceTest {

	@InjectMocks
	private TicketService ticketService;
	
	@Mock
	private TicketRepository ticketRepository;
	@Mock
	private UserService userService;
	@Mock
	private TripService tripService;
	@Mock
	private PaymentServiceClient paymentServiceClient;
	@Mock
	private RabbitTemplate rabbitTemplate;
	@Mock
	private TicketBookingTicketQueue ticketQueue;
	@Mock
	private TicketConverter ticketConverter;
	
	
	
	
	
}
