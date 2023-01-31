package com.ticketbooking.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketbooking.model.Ticket;
import com.ticketbooking.model.Trip;
import com.ticketbooking.model.User;
import com.ticketbooking.model.enums.PaymentType;
import com.ticketbooking.model.enums.TransportType;
import com.ticketbooking.model.enums.TripStatus;
import com.ticketbooking.model.enums.UserType;
import com.ticketbooking.service.TicketService;

@WebMvcTest(TicketController.class)
public class TicketControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TicketService ticketService;

	private ObjectMapper mapper = new ObjectMapper();

	@Test
	void it_should_create() throws Exception {
		// given
		Mockito.when(ticketService.create(Mockito.anyList(), Mockito.eq(Integer.valueOf(1)),
				Mockito.eq(Integer.valueOf(5)), Mockito.eq(PaymentType.CREDITCARD))).thenReturn(getTickets());
		// when
		String request = mapper.writeValueAsString(getTickets());
		ResultActions resultActions = mockMvc.perform(post("/tickets?userId=1&tripId=5&paymentType=CREDITCARD")
				.content(request).contentType(MediaType.APPLICATION_JSON));
		// then
		resultActions.andExpect(status().isCreated()).andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[0].id").value(3)).andExpect(jsonPath("$[0].name").value("huseyin"))
				.andExpect(jsonPath("$[0].surname").value("karaman"))
				.andExpect(jsonPath("$[0].identityNumber").value("123456789"));
	}

	@Test
	public void it_should_get_by_user_id() throws Exception {
		// given
		Mockito.when(ticketService.findByUserId(Mockito.eq(1))).thenReturn(getTickets());
		// when
		ResultActions resultActions = mockMvc.perform(get("/tickets/1"));
		// then
		resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[0].id").value(3)).andExpect(jsonPath("$[0].name").value("huseyin"))
				.andExpect(jsonPath("$[0].surname").value("karaman"))
				.andExpect(jsonPath("$[0].identityNumber").value("123456789"))
				.andExpect(jsonPath("$[0].seatNumber").value(11))
				.andExpect(jsonPath("$[0].user.username").value("test"))
				.andExpect(jsonPath("$[0].user.email").value("test@gmail.com"))
				.andExpect(jsonPath("$[0].user.mobileNumber").value("123456789"));
	}

	@Test
	public void it_should_get_tickets() throws Exception {
		// given
		Mockito.when(ticketService.findAll()).thenReturn(getTickets());
		// when
		ResultActions resultActions = mockMvc.perform(get("/tickets"));
		// then
		resultActions.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[1].id").value(4))
				.andExpect(jsonPath("$[1].name").value("huseyin"))
				.andExpect(jsonPath("$[1].surname").value("karaman"))
				.andExpect(jsonPath("$[1].seatNumber").value(12))
				.andExpect(jsonPath("$[1].identityNumber").value("123456789"))
				.andExpect(jsonPath("$[1].user.username").value("test"))
				.andExpect(jsonPath("$[1].user.email").value("test@gmail.com"))
				.andExpect(jsonPath("$[1].user.mobileNumber").value("123456789"));

	}

	private List<Ticket> getTickets() {
		return List.of(getTicket(3, 11), getTicket(4, 12));
	};

	private Ticket getTicket(int id, int seatNumber) {
		return Ticket.builder().id(id).birthDate(LocalDate.now()).seatNumber(seatNumber).name("huseyin")
				.surname("karaman").identityNumber("123456789").user(getUser(1)).trip(getTrip(5)).build();
	};

	private User getUser(int id) {
		return User.builder().id(id).username("test").email("test@gmail.com").mobileNumber("123456789")
				.userType(UserType.INDIVIDUAL).build();
	}

	private Trip getTrip(int id) {
		return Trip.builder().capacity(45).departureDate(LocalDateTime.of(2023, 12, 5, 5, 20)).id(id).from("istanbul")
				.to("ankara").price(100).transportType(TransportType.BUS).tripStatus(TripStatus.ACTIVE).build();
	}
}
