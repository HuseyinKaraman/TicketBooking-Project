package com.ticketbooking.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ticketbooking.converter.TripConverter;
import com.ticketbooking.repository.TripRepository;

@ExtendWith(SpringExtension.class)
public class TripServiceTest {

	@InjectMocks
	private TripService tripService;
	@Mock
	private TripRepository tripRepository;
	@Mock
	private TripConverter tripConverter;
	@Mock
	private UserService userService;
	
}
