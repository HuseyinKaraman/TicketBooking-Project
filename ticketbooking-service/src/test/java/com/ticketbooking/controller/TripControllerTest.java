package com.ticketbooking.controller;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketbooking.dto.request.UpdateTripRequest;
import com.ticketbooking.model.Trip;
import com.ticketbooking.model.enums.TransportType;
import com.ticketbooking.model.enums.TripStatus;
import com.ticketbooking.service.TripService;
import com.ticketbooking.service.UserService;

@WebMvcTest(TripController.class)
public class TripControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TripService tripService;

	private ObjectMapper mapper = new ObjectMapper();

	@Test
	void it_should_create() throws Exception {
		// given
		Mockito.when(tripService.create(Mockito.any(Trip.class), Mockito.eq(1))).thenReturn(
				getTrip(1, 45, 250, TransportType.BUS, LocalDateTime.of(2023, 5, 6, 5, 20), TripStatus.ACTIVE));

		// when
		String request = mapper.writeValueAsString(
				getTrip(1, 45, 250, TransportType.BUS, LocalDateTime.of(2023, 5, 6, 5, 20), TripStatus.ACTIVE));
		ResultActions resultActions = mockMvc
				.perform(post("/trips?userId=1").content(request).contentType(MediaType.APPLICATION_JSON));

		// then
		resultActions.andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.from").value("istanbul")).andExpect(jsonPath("$.to").value("ankara"))
				.andExpect(jsonPath("$.price").value(250)).andExpect(jsonPath("$.capacity").value(45))
				.andExpect(jsonPath("$.transportType").value(TransportType.BUS.toString()))
				.andExpect(jsonPath("$.tripStatus").value(TripStatus.ACTIVE.toString()));

	}

	@Test
	public void it_should_get_trips() throws Exception {
		Mockito.when(tripService.findAll()).thenReturn(getTrips());

		ResultActions resultActions = mockMvc.perform(get("/trips"));

		resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(3))
				.andExpect(jsonPath("$[1].id").value(2)).andExpect(jsonPath("$[1].from").value("istanbul"))
				.andExpect(jsonPath("$[1].to").value("ankara")).andExpect(jsonPath("$[1].price").value(500))
				.andExpect(jsonPath("$[1].capacity").value(189))
				.andExpect(jsonPath("$[1].transportType").value(TransportType.FLIGHT.toString()))
				.andExpect(jsonPath("$[1].tripStatus").value(TripStatus.ACTIVE.toString()));

	}

	@Test
	public void it_should_update() throws Exception {
		// given
		Mockito.when(tripService.update(Mockito.any(UpdateTripRequest.class),Mockito.anyInt())).thenReturn(
				getTrip(1, 45, 350, TransportType.BUS, LocalDateTime.of(2023, 5, 6, 5, 20, 0), TripStatus.CANCELED));

		// when
		String request = mapper.writeValueAsString(
				updateTripRequest(1, 350, LocalDateTime.of(2023, 5, 6, 5, 20, 0), TripStatus.CANCELED));

		ResultActions resultActions = mockMvc
				.perform(post("/trips/update?userId=2").content(request).contentType(MediaType.APPLICATION_JSON));

		// then
		resultActions.andExpect(status().isOk())
				.andExpect(jsonPath("$.from").value("istanbul"))
				.andExpect(jsonPath("$.to").value("ankara"))
				.andExpect(jsonPath("$.departureDate").value("06-05-2023 05:20:00"))
				.andExpect(jsonPath("$.transportType").value(TransportType.BUS.toString()))
				.andExpect(jsonPath("$.tripStatus").value(TripStatus.CANCELED.toString()));
	}

	@Test
	public void it_should_search() throws Exception {
		// given
		Mockito.when(tripService.searchTrip(Mockito.eq("istanbul"), Mockito.eq("ankara"),
				Mockito.any(TransportType.class), Mockito.nullable(LocalDateTime.class)))
				.thenReturn(List.of(getTrips().get(1), getTrips().get(2)));

		ResultActions resultActions = mockMvc
				.perform(get("/trips/search?from=istanbul&to=ankara&transportType=FLIGHT"));

		resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[1].id").value(3)).andExpect(jsonPath("$[1].from").value("istanbul"))
				.andExpect(jsonPath("$[1].to").value("ankara")).andExpect(jsonPath("$[1].price").value(750))
				.andExpect(jsonPath("$[1].capacity").value(189))
				.andExpect(jsonPath("$[1].transportType").value(TransportType.FLIGHT.toString()))
				.andExpect(jsonPath("$[1].tripStatus").value(TripStatus.ACTIVE.toString()));

	}

	@Test
	public void it_should_search_by_date() throws Exception {
		// given
		Mockito.when(tripService.searchTrip(Mockito.any(LocalDateTime.class)))
				.thenReturn(List.of(getTrips().get(0), getTrips().get(1)));

		ResultActions resultActions = mockMvc.perform(get("/trips/searchByDate?dateTime=2023-05-06T05:20:00.000"));

		resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[0].id").value(1)).andExpect(jsonPath("$[0].from").value("istanbul"))
				.andExpect(jsonPath("$[0].to").value("ankara")).andExpect(jsonPath("$[0].price").value(250))
				.andExpect(jsonPath("$[1].price").value(500)).andExpect(jsonPath("$[0].capacity").value(45))
				.andExpect(jsonPath("$[1].capacity").value(189))
				.andExpect(jsonPath("$[0].transportType").value(TransportType.BUS.toString()))
				.andExpect(jsonPath("$[1].transportType").value(TransportType.FLIGHT.toString()));

	}

	@Test
	public void it_should_delete() throws Exception {
		ResultActions resultActions = mockMvc.perform(post("/trips/delete?userId=1&tripId=2"));

		resultActions.andExpect(status().isOk());
		Mockito.verify(tripService, times(1)).delete(Mockito.anyInt(), Mockito.anyInt());
	}

	private Trip getTrip(int id, int capacity, int price, TransportType transportType, LocalDateTime dateTime,
			TripStatus tripStatus) {
		return Trip.builder().capacity(capacity).departureDate(dateTime).id(id).from("istanbul").to("ankara")
				.price(price).transportType(transportType).tripStatus(tripStatus).build();
	}

	private UpdateTripRequest updateTripRequest(int id, int price, LocalDateTime dateTime, TripStatus tripStatus) {
		return UpdateTripRequest.builder().id(id).from("istanbul").to("ankara").price(price).departureDate(dateTime)
				.tripStatus(tripStatus).build();
	}

	private List<Trip> getTrips() {
		return List.of(getTrip(1, 45, 250, TransportType.BUS, LocalDateTime.of(2023, 5, 6, 5, 20), TripStatus.ACTIVE),
				getTrip(2, 189, 500, TransportType.FLIGHT, LocalDateTime.of(2023, 5, 6, 5, 20), TripStatus.ACTIVE),
				getTrip(3, 189, 750, TransportType.FLIGHT, LocalDateTime.of(2023, 12, 7, 5, 20), TripStatus.ACTIVE));
	}

}
