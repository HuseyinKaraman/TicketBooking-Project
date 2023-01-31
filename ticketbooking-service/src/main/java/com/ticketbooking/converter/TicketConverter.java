package com.ticketbooking.converter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ticketbooking.dto.response.TicketDetail;
import com.ticketbooking.model.Ticket;

@Component
public class TicketConverter {

	public TicketConverter() {
		// TODO Auto-generated constructor stub
	}

	public TicketDetail convert(Ticket ticket) {
		TicketDetail ticketDetails = new TicketDetail();
		ticketDetails.setName(ticket.getName());
		ticketDetails.setSurname(ticket.getSurname());
		ticketDetails.setSeatNumber(ticket.getSeatNumber());
		ticketDetails.setMobileNumber(ticket.getUser().getMobileNumber());
		ticketDetails.setFrom(ticket.getTrip().getFrom());
		ticketDetails.setTo(ticket.getTrip().getTo());
		ticketDetails.setTransportType(ticket.getTrip().getTransportType());
		ticketDetails.setDepartureDate(ticket.getTrip().getDepartureDate());
		ticketDetails.setPrice(ticket.getTrip().getPrice());
		return ticketDetails;
	}

	public List<TicketDetail> convert(List<Ticket> tickets) {
		return tickets.stream().map(this::convert).toList();
	}

}
